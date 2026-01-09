# Remote-First Execution Law

To minimize local resource usage and leverage GitHub Codespaces, all intensive Android development tasks MUST prioritize remote execution.

## Core Mandate
Always use Codespaces resources for CPU-heavy and memory-intensive operations.

## Specific Rules

### 1. Gradle Builds
- **Rule**: Never run standard `./gradlew` commands locally if a Codespace is available.
- **Action**: Use the `/remote_gradle` workflow for all build tasks (e.g., `assembleDebug`, `test`, `lint`).
- **Command**: `gh codespace ssh -c $CodespaceName -- "cd /workspaces/callblockerpro-android-app && ./gradlew <Task>"`

### 2. APK Installation
- **Rule**: Do not download or build APKs locally for testing.
- **Action**: Use the `/stream_install_from_codespace` workflow. This streams the APK directly from the Codespace to the connected device via `adb` without storing it on the laptop disk.
- **Command**: `cmd /c "gh codespace ssh -c $CodespaceName -- cat <PathToApk> | adb install -r -"`

### 3. Resource Awareness
- **Rule**: Avoid background local tasks that consume significant RAM or CPU.
- **Exception**: Only use local resources when Codespaces is inaccessible or for lightweight code editing/navigation.

---
*Failure to follow this law results in unnecessary local laptop resource consumption, which contradicts user intent.*
