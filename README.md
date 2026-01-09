# CallBlockerPro

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-1.5.10-4285F4.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**CallBlockerPro** is a premium Android call blocking and management application built with modern Android development practices. It features a luxury dark UI design, intelligent call screening, and comprehensive privacy controls.

---

## ✨ Features

- 📵 **Smart Call Blocking**: Three blocking modes (Neutral, Whitelist, Blocklist)
- 📊 **Call Activity Tracking**: Comprehensive log of all incoming calls
- 📋 **List Management**: Organize contacts into blocklist and allowlist
- ⏰ **Auto Scheduler**: Time-based blocking rules
- 💾 **Backup & Restore**: Automatic backup with cloud sync (coming soon)
- 🎨 **Luxury Dark UI**: Premium design with glassmorphism effects
- 🔒 **Privacy First**: All data stored locally with encryption

---

## 🏗️ Architecture

CallBlockerPro follows **Clean Architecture** principles with strict layer separation.

### Layer Breakdown

**UI Layer** (`ui/`)
- Jetpack Compose screens
- ViewModels with StateFlow
- Custom design system
- Feature-specific components

**Domain Layer** (`domain/`)
- Business entities
- Use cases
- Repository interfaces
- Call decision logic

**Data Layer** (`data/`)
- Room database implementation
- Repository implementations
- Data mappers
- Local preferences

---

## 🛠️ Tech Stack

| Category | Technology |
|:---|:---|
| **Language** | Kotlin 1.9.22 |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt |
| **Database** | Room |
| **Async** | Coroutines + Flow |
| **Navigation** | Navigation Compose (Type-Safe) |
| **Background Work** | WorkManager |
| **Serialization** | kotlinx.serialization |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- Minimum Android 8.0 (API 26)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/CallBlockerPro.git
   cd CallBlockerPro
   ```

2. **Open in Android Studio**
   - File → Open → Select the project directory
   - Wait for Gradle sync to complete

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   ```bash
   ./gradlew installDebug
   ```

---

## 🧪 Testing

### Run All Tests
```bash
./gradlew test                      # Unit tests
./gradlew connectedAndroidTest      # Instrumented tests
```

### Run Specific Test Suites
```bash
# Domain logic tests
./gradlew test --tests "com.example.callblockerpro.domain.*"

# UI tests
./gradlew connectedAndroidTest --tests "com.example.callblockerpro.ui.*"
```

---

## 🔧 Build Variants

| Variant | Purpose | ProGuard | Debuggable |
|:---|:---|:---:|:---:|
| **debug** | Development | ❌ | ✅ |
| **release** | Production | ✅ | ❌ |

### Build Release APK
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

---

## 🎨 Design System

CallBlockerPro features a **Luxury Dark** design system:

### Typography
- **Font Family**: Manrope (5 weights)
- **Scale**: Material 3 type scale (13 styles)

### Colors
- **PrimaryGold**: `#D4AF37` - Metallic gold accent
- **BackgroundDark**: `#0A0A0A` - Pure black background
- **SurfaceCard**: `#1C1C1E` - Card surfaces

---

## 🔒 Security & Privacy

- ✅ All data stored locally on device
- ✅ ProGuard code obfuscation in release builds
- ✅ No analytics or tracking
- ✅ Database migrations to preserve user data

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Add KDoc comments for public APIs
- Write tests for new features

---

## 📄 License

MIT License - see LICENSE file for details

---

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [Hilt](https://dagger.dev/hilt/) - Dependency injection
- [Room](https://developer.android.com/training/data-storage/room) - Database ORM
- [Material Design 3](https://m3.material.io/) - Design language

---

**Made with ❤️ using Jetpack Compose**
