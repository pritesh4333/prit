-optimizations !code/simplification/arithmetic,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontusemixedcaseclassnames
-dontpreverify
-verbose
-ignorewarnings
-mergeinterfacesaggressively
-overloadaggressively
-repackageclasses in.co.vyapari

-dontnote android.net.http.**
-dontnote org.apache.commons.**
-dontnote org.apache.http.**

#For ACRA
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keep class org.acra.ACRA {*;}
-keep class org.acra.ReportingInteractionMode {*;}
-keep public class org.acra.ErrorReporter{public void addCustomData(java.lang.String,java.lang.String);}
-keep public class org.acra.ErrorReporter{public org.acra.ErrorReporter$ReportsSenderWorker handleSilentException(java.lang.Throwable);}
#End Acra Modification

#For External Libraries
-keep class javax.** { *; }
-keep class org.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.android.apps.analytics.**{ *; }
-keep public class com.google.** {*;}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }

#-keep class in.co.vyapari.middleware.** {*;}
-keep class in.co.vyapari.model.** {*;}
-keep class in.co.vyapari.ui.app.** {*;}
-keep class in.co.vyapari.ui.listener.** {*;}
#-keep class in.co.vyapari.database.** {*;}
-keep class in.co.vyapari.receiver.** {*;}
#-keep class in.co.vyapari.util.** {*;}
-keep class in.co.vyapari.analytics.** {*;}
#-keep class in.co.vyapari.constant.** {*;}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
	public static *** i(...);
	public static *** w(...);
	public static *** e(...);
    public static *** v(...);
}

#end Edit

-keep class android.support.v4.** { *; }

-keep public class * extends android.support.v4.**

-keep public class * extends android.support.v7.**

-keep public class * extends android.support.v7.widget.**

-dontwarn android.support.v7.**

-keep class android.support.v7.** { *; }

-keep interface android.support.v7.** { *; }

-keep class android.support.v7.widget.SearchView { *; }

-keep public class * extends android.app.SearchManager

-keep public class * extends android.app.Fragment

-keep public class * extends android.app.Activity

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference

-keep class * extends DefaultHttpClient
-keep class com.facebook.** {
   *;
}
-keepclassmembers class * extends android.os.AsyncTask {
    protected void onPreExecute();
    protected *** doInBackground(...);
    protected void onPostExecute(...);
}

-keepnames class * implements java.io.Serializable

-keepnames class * implements android.os.Parcelable

-keepnames class * implements android.os.Parcel

-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

# Keep names - _class method names. Keep all .class method names. This may be
# useful for libraries that will be obfuscated again with different obfuscators.
-keepclassmembers,allowshrinking class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String,boolean);
}

# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8

# Keep the pojos used by GSON or Jackson
-keep class com.futurice.project.models.pojo.** { *; }

# Keep GSON stuff
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

# Keep Jackson stuff
-keep class org.codehaus.** { *; }
-keep class com.fasterxml.jackson.annotation.** { *; }

# Keep these for GSON and Jackson
-keepattributes EnclosingMethod

#OneSignal

-dontwarn com.onesignal.**

# These 2 methods are called with reflection.
-keep class com.google.android.gms.common.api.GoogleApiClient {
    void connect();
    void disconnect();
}

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-keep class com.onesignal.ActivityLifecycleListenerCompat** {*;}


# Observer backcall methods are called with reflection
-keep class com.onesignal.OSSubscriptionState {
    void changed(com.onesignal.OSPermissionState);
}

-keep class com.onesignal.OSPermissionChangedInternalObserver {
    void changed(com.onesignal.OSPermissionState);
}

-keep class com.onesignal.OSSubscriptionChangedInternalObserver {
    void changed(com.onesignal.OSSubscriptionState);
}

-keep class ** implements com.onesignal.OSPermissionObserver {
    void onOSPermissionChanged(com.onesignal.OSPermissionStateChanges);
}

-keep class ** implements com.onesignal.OSSubscriptionObserver {
    void onOSSubscriptionChanged(com.onesignal.OSSubscriptionStateChanges);
}

-keep class com.onesignal.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.ApexHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.AsusHomeLauncher { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.DefaultBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.EverythingMeHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.HuaweiHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.LGHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NewHtcHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NovaHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.OPPOHomeBader { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SamsungHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SonyHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.VivoHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.XiaomiHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.ZukHomeBadger { <init>(...); }


-dontwarn com.amazon.**

# Proguard ends up removing this class even if it is used in AndroidManifest.xml so force keeping it.
-keep public class com.onesignal.ADMMessageHandler {*;}

-keep class com.onesignal.JobIntentService$* {*;}

-keep class com.onesignal.OneSignalUnityProxy {*;}

# Keep Picasso
-keep class com.squareup.picasso.** { *; }
-keepclasseswithmembers class * {
    @com.squareup.picasso.** *;
}
-keepclassmembers class * {
    @com.squareup.picasso.** *;
}

-keepclassmembers class * extends com.stephentuso.welcome.WelcomeActivity {
    public static java.lang.String welcomeKey();
}

#-keep class com.apptracktion.OkResult {*;}
#-keep class com.apptracktion.RequestBody {*;}

#eventbus

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#butterknife

# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keep class **$$ViewInjector { *; }
#-keepnames class * { @butterknife.InjectView *;}

# Intercom
-keep class com.intercom.** { *; }
-keep class io.intercom.android.** { *; }
-keep class com.bumptech.glide.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.squareup.otto.** { *; }
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class retrofit2.** { *; }
-dontwarn com.intercom.**
-dontwarn intercom.**
-dontwarn io.intercom.**
-dontwarn com.bumptech.glide.**
-dontwarn com.google.gson.**
-dontwarn com.squareup.otto.**
-dontwarn io.intercom.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#-verbose
# Use ProGuard only to get rid of unused classes
#-dontobfuscate
#-dontoptimize
#-keepattributes !in.co.vyapari.**

# Keep the entry point to this library, see META-INF\services\javax.annotation.processing.Processor
-keep class com.bumptech.glide.annotation.compiler.GlideAnnotationProcessor


# "duplicate definition of library class"
#-dontnote sun.applet.**
# "duplicate definition of library class"
#-dontnote sun.tools.jar.**
# Reflective accesses in com.google.common.util.concurrent.* and some others
-dontnote com.bumptech.glide.repackaged.com.google.common.**
# com.google.common.collect.* and some others (….common.*.*)
-dontwarn com.google.j2objc.annotations.Weak
# com.google.common.util.concurrent.FuturesGetChecked$GetCheckedTypeValidatorHolder$ClassValueValidator
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn **