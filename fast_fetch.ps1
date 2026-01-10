param(
    [Parameter(Mandatory=$true)]
    [string]$FilePath
)

# Configuration
$CodespaceName = "antigravitycore-pj5pvx479j9wfrq99"
$RemoteRoot = "/workspaces/callblockerpro-android-app"

# Normalize path
$RelPath = $FilePath -replace "^[.\\]+", ""
$RemotePath = "$RemoteRoot/" + $RelPath.Replace("\", "/")
$LocalPath = Join-Path $PSScriptRoot $RelPath

Write-Host " [Fetching] remote://$RemotePath -> $RelPath" -ForegroundColor Cyan

try {
    # Stream from Remote (Base64 encoded to preserve integrity)
    $B64 = gh codespace ssh -c $CodespaceName -- "base64 $RemotePath" 2>$null

    if (-not $B64) {
        Write-Error " [Failed] Could not read file or file is empty."
        exit 1
    }

    # Decode and Save Encoded Content
    # Remove any potential whitespace/newlines from the received string
    $CleanB64 = $B64 -replace '\s+', ''
    $Bytes = [Convert]::FromBase64String($CleanB64)
    
    $ParentDir = Split-Path $LocalPath -Parent
    if (-not (Test-Path $ParentDir)) {
        New-Item -ItemType Directory -Force -Path $ParentDir | Out-Null
    }

    [System.IO.File]::WriteAllBytes($LocalPath, $Bytes)

    Write-Host " [Success] Fetched." -ForegroundColor Green
} catch {
    Write-Error " [Error] $_"
}
