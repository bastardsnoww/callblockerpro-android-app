---
description: Run Gradle commands remotely on GitHub Codespace
---

This workflow allows you to execute any Gradle task (e.g., `assembleDebug`, `test`, `lint`) on your remote GitHub Codespace, offloading all CPU and RAM usage from your local machine.

**Prerequisites:**
- `gh` CLI authenticated
- Active Codespace for this repository
- (Optional) `$env:GH_CODESPACE_NAME` set locally

**Execution:**
Pass the Gradle task(s) you want to run as arguments.

```powershell
# Get Codespace name from environment or prompt
$CodespaceName = $env:GH_CODESPACE_NAME
if (-not $CodespaceName) {
    $CodespaceName = (gh codespace list --json name --jq '.[0].name')
    Write-Host "Using Codespace: $CodespaceName (Set `$env:GH_CODESPACE_NAME` to avoid this lookup)"
}

$GradleTask = "assembleDebug" # Change this to your desired task

Write-Host "Executing './gradlew $GradleTask' on $CodespaceName..."
gh codespace ssh -c $CodespaceName -- "cd /workspaces/callblockerpro-android-app && ./gradlew $GradleTask"
```

**How it works:**
1. Connects to the remote Codespace via SSH using the GitHub CLI.
2. Changes directory to the project root in the Codespace.
3. Executes the standard Gradle wrapper with the specified tasks.
4. Streams the build output back to your local terminal.
