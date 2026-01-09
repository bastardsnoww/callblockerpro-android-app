# Remote-First Law 📜

To maintain maximum performance and battery life on local machines, this project follows a **Remote-First** resource usage policy. All heavy lifting MUST be offloaded to GitHub Codespaces.

## ⚖️ The Laws

1.  **No Local Gradle Builds**: Running `./gradlew build`, `./gradlew assemble`, or any long-running Gradle task locally is prohibited. Use the `remote-gradle` workflow or script instead.
2.  **No Local Dependency Resolution**: Downloading and caching gigabytes of dependencies must happen in the Codespace.
3.  **No Local Test Execution**: Unit tests and linting must be executed in the cloud.
4.  **Local Machine is for UI only**: Your laptop is for the IDE (VS Code / Android Studio) and communicating with the physical device via `adb`.

## 🛠️ How to Follow the Law

### 1. Set Your Codespace Variable
To make remote commands seamless, set your `GH_CODESPACE_NAME` environment variable:
```powershell
[System.Environment]::SetEnvironmentVariable('GH_CODESPACE_NAME', 'your-codespace-name-here', 'User')
```

### 2. Use Remote Gradle
Instead of `./gradlew <task>`, use the provided PowerShell helper:
```powershell
./scripts/remote-gradle.ps1 <task>
```

### 3. Stream Install
To test on a device, use the stream install workflow which pulls the APK directly from the Codespace into the device's memory, bypassing your local disk:
```powershell
# Use the Antigravity workflow: /stream_install_from_codespace
```

## 🔋 Benefits
- **Zero Local CPU Usage**: Your laptop fan stays silent.
- **Save Disk Space**: Multi-gigabyte `build/` folders and `.gradle` caches stay in the cloud.
- **Fast Network**: Codespaces have high-speed backbone connections for downloading dependencies.
