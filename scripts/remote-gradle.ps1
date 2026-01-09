param (
    [Parameter(Mandatory=$false, Position=0)]
    [string]$GradleTask = "assembleDebug"
)

# Get Codespace name from environment or look it up
$CodespaceName = $env:GH_CODESPACE_NAME
if (-not $CodespaceName) {
    Write-Host "Looking up active Codespace..." -ForegroundColor Cyan
    # Get the raw JSON list and parse it in PowerShell instead of using gh's --jq
    $codespacesJson = (gh codespace list --json name,state | ConvertFrom-Json)
    $CodespaceName = ($codespacesJson | Where-Object { $_.state -eq "Available" } | Select-Object -ExpandProperty name -First 1)
    
    if (-not $CodespaceName) {
        Write-Error "No active Codespace found. Please start a Codespace first."
        exit 1
    }
    Write-Host "Using Codespace: $CodespaceName" -ForegroundColor Gray
    Write-Host "(💡 Tip: Set `$env:GH_CODESPACE_NAME` to skip lookup)" -ForegroundColor DarkGray
}

Write-Host "🚀 Running './gradlew $GradleTask' on remote Codespace..." -ForegroundColor Green

# Use CMD to ensure stable piping if needed in future, 
# but for simple command execution SSH is fine.
gh codespace ssh -c $CodespaceName -- "cd /workspaces/callblockerpro-android-app && ./gradlew $GradleTask"

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Remote build successful!" -ForegroundColor Green
} else {
    Write-Host "❌ Remote build failed." -ForegroundColor Red
}
