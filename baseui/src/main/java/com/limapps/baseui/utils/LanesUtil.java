package com.limapps.baseui.utils;


import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.limapps.common.VersionUtils;

import java.util.Arrays;

public class LanesUtil {

    private static final String TAG = "LanesUtil";
    public static final String LOW = "low";
    public static final String HIGH = "high";
    public static final String MEDIUM = "medium";

    private static LanesUtil lanesUtil;

    private int screenWidth;
    private int screenHeight;

    private String densityName;

    private String densityLinear;

    private int density;
    private float screenDensity;

    private LanesUtil(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setOrientation(windowManager);
        setDensity(windowManager);
    }

    private void setOrientation(WindowManager windowManager) {

        DisplayMetrics metrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(metrics);

        int x = metrics.widthPixels;
        int y = metrics.heightPixels;

        screenWidth = Math.min(x, y);
        screenHeight = Math.max(x, y);
    }

    public static synchronized void init(Context context) {
        if (lanesUtil == null) {
            lanesUtil = new LanesUtil(context);
        }
    }

    public static LanesUtil get() {
        return lanesUtil;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private void setDensity(WindowManager windowManager) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        density = metrics.densityDpi;
        screenDensity = metrics.density;
    }

    public String getDensityName() {

        if (densityName != null) return densityName;

        switch (density) {
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_XHIGH:
                densityName = MEDIUM;
                densityLinear = MEDIUM;
                break;
            case DisplayMetrics.DENSITY_TV:
                densityName = MEDIUM;
                densityLinear = MEDIUM;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityName = MEDIUM;
                densityLinear = MEDIUM;
                break;
            case DisplayMetrics.DENSITY_LOW:
                densityName = LOW;
                densityLinear = LOW;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_XXXHIGH:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_560:
                densityName = HIGH;
                densityLinear = HIGH;
                break;
            default:
                densityName = MEDIUM;
                densityLinear = MEDIUM;
                break;
        }

        return densityName;
    }

    public String getProductQuality() {
        return LOW;
    }

    public String getDensityLinear() {
        getDensityName();
        return densityLinear;
    }

    public float getScreenDensity() {
        return screenDensity;
    }

    public String getABI() {
        return VersionUtils.isLollipop() ? Arrays.toString(Build.SUPPORTED_ABIS) : Build.CPU_ABI;
    }

}