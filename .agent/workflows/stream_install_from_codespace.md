---
description: Stream install APK from Codespace to local device without saving file
---

This workflow streams the APK binary directly from the remote Codespace to the connected local Android device using standard input piping. This effectively installs the app without ever saving an `.apk` file to your local disk.

**Prerequisites:**
- `gh` CLI authenticated
- `adb` installed and device connected
- Codespace running

**Command (PowerShell Safe wrapper):**
To avoid PowerShell string encoding corruption of the binary APK data, we use `cmd.exe` for the piping.

```powershell
# Get Codespace name from environment or look it up
$CodespaceName = $env:GH_CODESPACE_NAME
if (-not $CodespaceName) {
    $CodespaceName = (gh codespace list --json name,state --jq '.[] | select(.state=="Available") | .name' | Select-Object -First 1)
}
$RemoteApkPath = "/workspaces/callblockerpro-android-app/app/build/outputs/apk/debug/app-debug.apk"

# Verify device is connected
adb devices

# Execute the stream installation
Write-Host "Streaming APK from Codespace: $CodespaceName"
cmd /c "gh codespace ssh -c $CodespaceName -- cat $RemoteApkPath | adb install -r -"
```

**How it works:**
1. `gh codespace ssh ... cat ...`: Outputs the raw bytes of the APK file to Standard Output (stdout).
2. `|`: Pipes that output to the next command.
3. `adb install -r -`: Reads the APK data from Standard Input (stdin) (denoted by the `-`) and installs it.
