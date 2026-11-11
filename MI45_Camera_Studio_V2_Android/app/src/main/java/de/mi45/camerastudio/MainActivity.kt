package de.mi45.camerastudio

import android.app.Activity
import android.os.Bundle
import android.webkit.*
import android.content.Intent
import android.util.Base64
import android.widget.Toast
import android.net.Uri

class MainActivity : Activity() {
    private lateinit var web: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private var pendingSave: Pair<String, ByteArray>? = null
    private val openFileCode = 101
    private val saveFileCode = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        setContentView(web)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = true
        web.settings.allowContentAccess = true
        web.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(view: WebView?, callback: ValueCallback<Array<Uri>>?, params: FileChooserParams?): Boolean {
                fileCallback?.onReceiveValue(null)
                fileCallback = callback
                val intent = params?.createIntent() ?: Intent(Intent.ACTION_GET_CONTENT).apply { type = "*/*"; addCategory(Intent.CATEGORY_OPENABLE) }
                try { startActivityForResult(intent, openFileCode) } catch (e: Exception) { fileCallback = null; callback?.onReceiveValue(null); return false }
                return true
            }
        }
        web.addJavascriptInterface(object {
            @JavascriptInterface fun saveFile(filename: String, base64: String, mime: String) {
                try {
                    val bytes = Base64.decode(base64, Base64.DEFAULT)
                    runOnUiThread {
                        pendingSave = mime to bytes
                        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = mime.ifBlank { "application/octet-stream" }
                            putExtra(Intent.EXTRA_TITLE, filename)
                        }
                        startActivityForResult(intent, saveFileCode)
                    }
                } catch (e: Exception) { runOnUiThread { Toast.makeText(this@MainActivity, "Export fehlgeschlagen", Toast.LENGTH_LONG).show() } }
            }
        }, "AndroidBridge")
        web.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == openFileCode) {
            fileCallback?.onReceiveValue(if (resultCode == RESULT_OK) WebChromeClient.FileChooserParams.parseResult(resultCode, data) else null)
            fileCallback = null
        } else if (requestCode == saveFileCode) {
            if (resultCode == RESULT_OK && data?.data != null) {
                try { pendingSave?.second?.let { contentResolver.openOutputStream(data.data!!)?.use { stream -> stream.write(it) } } }
                catch (e: Exception) { Toast.makeText(this, "Datei konnte nicht gespeichert werden", Toast.LENGTH_LONG).show() }
            }
            pendingSave = null
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }
}
