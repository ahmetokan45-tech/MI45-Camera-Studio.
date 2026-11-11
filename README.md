# MI45 Camera Studio V2.0.0 — Android

Eigenständige Offline-WebView-App mit integriertem HTML-Editor. Paket: `de.mi45.camerastudio`. Keine Berechtigungen, kein Root, kein Shizuku.

## APK bauen
1. Dieses Projekt in ein neues GitHub-Repository hochladen (inklusive `.github/workflows/android.yml`).
2. GitHub → Actions → **Build MI45 Camera Studio APK** → **Run workflow**.
3. Nach erfolgreichem Build unter **Artifacts** `MI45-Camera-Studio-V2.0.0-debug-APK` herunterladen. Darin liegt `app-debug.apk`.

Alternativ lokal mit Android Studio öffnen und `assembleDebug` ausführen.

## Status
Quellprojekt erstellt; Android-APK in dieser Umgebung nicht kompiliert oder auf Gerät getestet. Datei-Import via Android-Dateiauswahl, Export via Android-Speichern-Dialog implementiert, aber noch nicht gerätegetestet.
