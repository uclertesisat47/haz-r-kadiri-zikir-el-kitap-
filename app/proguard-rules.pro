# Kadiri El Kitabı — ProGuard Kuralları
# ==============================================================

# Temel Android/Jetpack tutma kuralları
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes Exceptions

# Hilt DI
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.InstallIn class * { *; }
-keep @dagger.hilt.android.HiltAndroidApp class * { *; }
-dontwarn dagger.hilt.**

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-dontwarn androidx.room.**

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**

# Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.kadiri.elkitabi.**$$serializer { *; }
-keepclassmembers class com.kadiri.elkitabi.** {
    *** Companion;
}
-keepclasseswithmembers class com.kadiri.elkitabi.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# DataStore
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Application sınıfı
-keep class com.kadiri.elkitabi.KadiriApplication { *; }
-keep class com.kadiri.elkitabi.MainActivity { *; }

# Tüm domain modelleri
-keep class com.kadiri.elkitabi.**.domain.model.** { *; }
-keep class com.kadiri.elkitabi.**.data.local.entities.** { *; }

# Broadcast Receiver
-keep class com.kadiri.elkitabi.core.receivers.** { *; }

# Service
-keep class com.kadiri.elkitabi.features.ilahi.PlaybackService { *; }

# Enum saklama
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Uyarıları sustur
-dontwarn com.google.errorprone.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.**
