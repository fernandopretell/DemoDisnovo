# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/diegoamezquita/Development/AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.limapps.base.models.** { *; }
-keep class com.limapps.base.support.model.** { *; }

-keepclassmembers class com.limapps.favor.models.** { *; }
-keepclassmembers class com.limapps.models.creation.** { *; }

-keep class com.google.android.gms.ads.** { *; }
-dontwarn okio.**

-keep class bo.app.** { *; }
-keep class com.appboy.** { *; }


-keepnames class com.facebook.drawee.backends.pipeline.Fresco
-keepnames class com.facebook.drawee.interfaces.DraweeController
-keepnames class com.facebook.drawee.view.SimpleDraweeView
-keepnames class com.facebook.drawee.backends.pipeline.Fresco
-keepnames class com.facebook.drawee.controller.BaseControllerListener
-keepnames class com.facebook.drawee.controller.ControllerListener
-keepnames class com.facebook.imagepipeline.image.ImageInfo