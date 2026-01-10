param(
    [string]$Message = "wip: auto-save $(Get-Date -Format 'yyyy-MM-dd HH:mm')"
)

# 1. Stage All Changes
Write-Host " [Git] Staging all changes..." -ForegroundColor Cyan
git add .

# 2. Commit
Write-Host " [Git] Committing..." -ForegroundColor Cyan
git commit -m "$Message"

# 3. Push (handled by post-commit hook usually, but explicit here for safety)
# If post-commit exists, this might be redundant but harmless.
if ($LASTEXITCODE -eq 0) {
    Write-Host " [Git] Pushing to origin..." -ForegroundColor Cyan
    git push
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host " [Success] Saved & Synced." -ForegroundColor Green
    } else {
        Write-Error " [Error] Push failed."
    }
} else {
    Write-Warning " [Git] Nothing to commit or commit failed."
}
