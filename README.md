# CallBlockerPro

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-1.7.6-4285F4.svg)](https://developer.android.com/jetpack/compose)
[![Version](https://img.shields.io/badge/Version-1.0.0-brightgreen.svg)](https://github.com/bastardsnow/callblockerpro-android-app/releases)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**CallBlockerPro** is a premium Android call blocking and management application built with modern Android development practices. It features a luxury dark UI design, intelligent call screening, and comprehensive privacy controls.

**🔒 100% Private & Local** - All data stays on your device. No internet access. No tracking.

---

## 📸 Project Gallery

<p align="center">
  <img src="docs/assets/verify_bg_2.png" width="45%" alt="Home Screen Dashboard" />
  <img src="docs/assets/verify_bg.png" width="45%" alt="Protection Lists" />
</p>

---

## ✨ Features

- 📵 **Smart Call Blocking**: Three blocking modes (Standard, Relaxed, Strict)
- 📊 **Call Activity Tracking**: Comprehensive log of all incoming calls
- 📋 **List Management**: Organize contacts into blocklist and allowlist
- ⏰ **Auto Scheduler**: Time-based blocking rules (Work Hours)
- 🛡️ **Scam Protection**: On-device spam detection
- 🎨 **Luxury Dark UI**: Premium design with glassmorphism effects
- 🔒 **Privacy First**: All data stored locally with SQLCipher encryption
- 🚫 **No Internet Access**: App has no INTERNET permission - complete privacy guarantee

---

## 🏗️ Architecture

CallBlockerPro follows **Clean Architecture** principles with strict layer separation.

### Layer Breakdown

**UI Layer** (`ui/`)
- Jetpack Compose screens
- ViewModels with StateFlow
- Custom Crystal Design System
- Feature-specific components

**Domain Layer** (`domain/`)
- Business entities
- Use cases
- Repository interfaces
- Call decision logic

**Data Layer** (`data/`)
- Room database with SQLCipher encryption
- Repository implementations
- Data mappers
- EncryptedSharedPreferences

---

## 🛠️ Tech Stack

| Category | Technology |
|:---|:---|
| **Language** | Kotlin 1.9.22 |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt |
| **Database** | Room + SQLCipher |
| **Async** | Coroutines + Flow |
| **Navigation** | Navigation Compose (Type-Safe) |
| **Background Work** | WorkManager |
| **Serialization** | kotlinx.serialization |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 11
- Android SDK 35
- Minimum Android 10 (API 29)
- Target Android 14 (API 35)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/bastardsnow/callblockerpro-android-app.git
   cd callblockerpro-android-app
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
./gradlew test --tests "com.callblockerpro.app.domain.*"

# UI tests
./gradlew connectedAndroidTest --tests "com.callblockerpro.app.ui.*"
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

CallBlockerPro features a **Crystal Design System** with luxury dark aesthetics:

### Typography
- **Font Family**: System fonts with Material 3 type scale
- **Scale**: 13 type styles from labelSmall to displayLarge

### Colors
- **Primary Stitch**: Cyan/Blue gradient
- **Neon Accents**: Red, Gold, Green, Purple
- **Background**: Pure black (#000000)
- **Surface**: Dark cards with glassmorphism

---

## 🔒 Security & Privacy

- ✅ **100% Local Data** - No cloud sync, no internet access
- ✅ **SQLCipher Encryption** - Bank-grade database encryption
- ✅ **EncryptedSharedPreferences** - Secure preference storage
- ✅ **No INTERNET Permission** - Technically impossible to transmit data
- ✅ **ProGuard/R8** - Code obfuscation in release builds
- ✅ **No Analytics** - Zero tracking or telemetry

### Privacy Policy
See [PRIVACY.md](PRIVACY.md) for our comprehensive privacy policy.

### Terms of Service
See [TERMS.md](TERMS.md) for terms of service.

---

## 📦 Release

**Current Version**: 1.0.0 (Build 10)

### What's New in 1.0.0
- ✅ Complete call blocking functionality
- ✅ Three blocking modes (Standard, Relaxed, Strict)
- ✅ Blocklist and Allowlist management
- ✅ Call logs with filtering
- ✅ Work Hours scheduling
- ✅ Premium dark UI with Crystal Design System
- ✅ 100% local data with SQLCipher encryption
- ✅ Privacy Policy and Terms of Service

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
- Use ktlint for code formatting

---

## 📄 License

MIT License - see LICENSE file for details

---

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [Hilt](https://dagger.dev/hilt/) - Dependency injection
- [Room](https://developer.android.com/training/data-storage/room) - Database ORM
- [SQLCipher](https://www.zetetic.net/sqlcipher/) - Database encryption
- [Material Design 3](https://m3.material.io/) - Design language

---

## 📞 Support

For questions, issues, or feature requests:
- **GitHub Issues**: [Create an issue](https://github.com/bastardsnow/callblockerpro-android-app/issues)
- **Email**: support@callblockerpro.app

---

**Made with ❤️ using Jetpack Compose**

**Privacy-First • Local-Only • No Tracking**
