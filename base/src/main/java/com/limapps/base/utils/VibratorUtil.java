package com.limapps.base.utils;

import android.content.Context;
import android.os.Vibrator;
import androidx.annotation.Nullable;

public class VibratorUtil {

    public static final int VIBRATE_SHORT_DURATION = 20;
    public static final int VIBRATE_LONG_DURATION = 50;

    public static void vibrate(@Nullable Context context, int duration) {
        if (context != null) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null && v.hasVibrator()) {
                v.vibrate(duration);
            }
        }
    }


}
