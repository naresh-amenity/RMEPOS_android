package com.rme.utils;

import android.util.Log;

public class LargeLog {
    public static void e(String tag, String content) {
        final int SEG_LENGTH = 3000;
        do {
            if (content.length() <= SEG_LENGTH) {
                Log.e(tag, content);
                break;
            }
            Log.e(tag, content.substring(0, SEG_LENGTH));
            content = content.substring(SEG_LENGTH);
        } while (!content.isEmpty());
    }
}
