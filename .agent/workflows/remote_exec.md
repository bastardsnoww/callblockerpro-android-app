---
description: Run any command on a remote GitHub Codespace
---

This workflow allows you to execute arbitrary commands on your remote GitHub Codespace, enabling a remote-first workflow for resource-intensive tasks.

**Prerequisites:**
- `gh` CLI authenticated
- Active Codespace

**Execution:**
Pass the command you want to run as an argument.

```powershell
# Configuration - Set your preferred codespace here or use environment variable
$CodespaceName = "antigravitycore-pj5pvx479j9wfrq99" 
if ($env:GH_CODESPACE_NAME) { $CodespaceName = $env:GH_CODESPACE_NAME }

$RemoteCommand = "uname -a" # Default command
# Check if arguments are provided (simple check for the first argument)
# In a real shell, arguments would be passed directly.
# For this workflow, we'll assume the user might manually edit or just run standard commands.
# BUT, to make it re-usable via tools, we expect the user to replace the command below.

# REPLACE THIS WITH YOUR COMMAND
$Cmd = "ls -la" 

Write-Host "Executing on $CodespaceName: $Cmd"
gh codespace ssh -c $CodespaceName -- "cd /workspaces/callblockerpro-android-app && $Cmd"
```
