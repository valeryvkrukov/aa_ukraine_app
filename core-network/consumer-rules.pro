# 1. Protecting Hilt and Dagger factories from being stripped by the R8 optimizer
-keep class * extends dagger.hilt.internal.GeneratedComponent
-keep class * implements dagger.hilt.internal.GeneratedComponent
-keep @dagger.hilt.internal.generates.GeneratedEntryPoint class *
-keep @dagger.hilt.InstallIn class *

# 2. Saving the generated KSP factories for our NetworkModule
-keep class org.aa.ukraine.core.network.di.** { *; }
-keep class org.aa.ukraine.core.network.JsoupParser { *; }

# 3. Configuring Jsoup for secure network operation
-keepattributes Signature, InnerClasses, EnclosingMethod, AnnotationDefault
-dontwarn org.jsoup.**
-keep class org.jsoup.** { *; }