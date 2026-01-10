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

if (-not (Test-Path $LocalPath)) {
    Write-Error "File not found: $LocalPath"
    exit 1
}

Write-Host " [Syncing] $RelPath -> remote://$RemotePath" -ForegroundColor Cyan

try {
    # Read and Encode
    $Bytes = [System.IO.File]::ReadAllBytes($LocalPath)
    $B64 = [Convert]::ToBase64String($Bytes)

    # Stream to Remote
    # Note: Using -n to avoid sending a newline with Write-Output is tricky in PS piping, 
    # but base64 -d -i (ignore garbage) usually handles it.
    $B64 | gh codespace ssh -c $CodespaceName -- "base64 -di > $RemotePath"

    if ($LASTEXITCODE -eq 0) {
        Write-Host " [Success] Synced." -ForegroundColor Green
    } else {
        Write-Error " [Failed] Exit Code: $LASTEXITCODE"
    }
} catch {
    Write-Error " [Error] $_"
}
