package com.thangcao.core.ble_services.utils;

import android.os.Build;

public class VersionUtils {
    private VersionUtils() {

    }
    public static boolean isHigherThanLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
