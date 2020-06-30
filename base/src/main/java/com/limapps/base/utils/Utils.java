package com.limapps.base.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.limapps.base.activities.BaseFragmentActivity;
import com.limapps.common.StringExtensionsKt;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final String FOUR_DECIMALS_PATTERN = "#.####";
    public static final String NO_DECIMALS_PATTERN = "#";
    public static final String NO_ACTIVE_CONNECTION = "NONE";

    public static void openBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void openPlayStoreOnApp(Context context) {
        String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String encodeImageToBase64(String imagePath) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String bitmapEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            bitmapEncoded = bitmapEncoded.replaceAll("\n", " ");
            bitmapEncoded = "data:image/png;base64," + bitmapEncoded;
            return bitmapEncoded;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppVersion(Context context) {
        String version = "0";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = String.valueOf(pInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        try {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public static boolean isPermissionGranted(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static String getDeviceOs() {
        return String.format("Android %d (%s)", Build.VERSION.SDK_INT, Build.VERSION.RELEASE);
    }

    public static String getDeviceModel() {
        return String.format("%s - %s", Build.BRAND, Build.MODEL);
    }

    public static String getAndroidID(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        LogUtil.e("AndroidDevice", "Android ID : " + androidId);
        return androidId;
    }

    public static Pair<String, String> getMemory(Application application) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }

        String free = String.valueOf(memoryInfo.availMem / 0x100000L);
        String total = String.valueOf(memoryInfo.totalMem / 0x100000L);
        return new Pair<>(free, total);
    }

    public static String networkType(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return (info != null) ? info.getTypeName() + info.getSubtypeName() : NO_ACTIVE_CONNECTION;
        } else {
            return NO_ACTIVE_CONNECTION;
        }
    }

    public static String networkCarrier(Application application) {
        TelephonyManager telephonyManager = ((TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE));
        return (telephonyManager != null) ? telephonyManager.getNetworkOperatorName() : NO_ACTIVE_CONNECTION;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @SuppressWarnings({"MissingPermission"})
    public static void startCallIntent(@Nullable String phone, Context context) {
        String permission = Manifest.permission.CALL_PHONE;
        if (Utils.isPermissionGranted(context, permission)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            // Don't be scared for this warning. It just asking for call permission but
            // it is already requested in validateCallPermission()
            context.startActivity(callIntent);
        }
    }

    public static void applyFontTab(ViewPager viewPager, TabLayout tabLayout, @LayoutRes int item) {
        Context context = viewPager.getContext();
        PagerAdapter adapter = viewPager.getAdapter();
        for (int i = 0, limit = adapter.getCount(); i < limit; i++) {
            TextView tv = (TextView) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(item, null);
            if (i == viewPager.getCurrentItem()) {
                tv.setSelected(true);
            }
            final String title = (String) adapter.getPageTitle(i);
            final String processedTitle;
            if (title == null) {
                processedTitle = "";
            } else {
                processedTitle = title;
            }
            tv.setText(StringExtensionsKt.upperCaseFirstLetter(processedTitle));
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(tv);
            }
        }
    }

    public static boolean hasKeyWithoutNullValue(@Nullable JSONObject object, String key) {
        return object != null && object.has(key) && !object.isNull(key);
    }

    public static DecimalFormat getDecimalFormatPattern(String pattern) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat;
    }

    public static DecimalFormat getDecimalFormatPatternDown(String pattern) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat;
    }

    public static BaseFragmentActivity scanForActivity(Context context) {
        return context instanceof BaseFragmentActivity ? (BaseFragmentActivity) context :
                context instanceof ContextWrapper ?
                        scanForActivity(((ContextWrapper) context).getBaseContext()) : null;
    }

    public static boolean isAppInstalled(Context context, String applicationPackage) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(applicationPackage);
        List<ResolveInfo> list = intent != null ?
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY) : null;
        return list != null && !list.isEmpty();
    }

    public static int convertPixelsToDp(int px, Context context) {
        return (int) (px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPixel(int dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}