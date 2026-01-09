# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keppt Hilt-generated classes - specific needs usually handled by lib
# -keep class dagger.hilt.** { *; }
# -keep class javax.inject.** { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.callblockerpro.app.**$$serializer { *; }
-keepclassmembers class com.callblockerpro.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.callblockerpro.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Navigation Compose routes (Serializable objects)
-keep @kotlinx.serialization.Serializable class com.callblockerpro.app.ui.** { *; }

# Compose - Rely on library consumer rules
# -keep class androidx.compose.** { *; }
# -dontwarn androidx.compose.**

# Coroutines - Rely on library consumer rules
# -keep class kotlinx.coroutines.** { *; }

# Keep Retrofit (if added in future)
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# Keep Gson TypeAdapters (if using Gson)
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep data classes used with Gson/Serialization
-keepclassmembers class com.callblockerpro.app.domain.model.** { *; }
-keepclassmembers class com.callblockerpro.app.data.local.entity.** { *; }

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Keep custom exceptions
-keep public class * extends java.lang.Exception

# Keep CallBlockingService to ensure it's visible to the system for Default App selection
-keep class com.callblockerpro.app.service.** { *; }
-keep public class * extends android.telecom.CallScreeningService { *; }

# Optimization settings
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Fix for Missing ErrorProne annotations (referenced by Tink/Security-Crypto)
-dontwarn com.google.errorprone.annotations.**

# Tink / Security-Crypto rules (CRITICAL for EncryptedSharedPreferences)
-keep class com.google.crypto.tink.** { *; }
-keep class com.google.crypto.tink.shaded.protobuf.** { *; }
-keepnames class com.google.crypto.tink.shaded.protobuf.** { *; }
-dontwarn com.google.crypto.tink.shaded.protobuf.**

# Add rules for androidx.security.crypto
-keep class androidx.security.crypto.** { *; }

# Lower optimization for stability in release
-optimizationpasses 1

# Uncomment for debugging ProGuard issues
# -printconfiguration proguard-config.txt
# -printmapping proguard-mapping.txt
# -printseeds proguard-seeds.txt
# -printusage proguard-usage.txt

# Keep SQLCipher (CRITICAL for database)
-keep class net.sqlcipher.** { *; }
-keep class net.zetetic.** { *; }
