package com.mrgrd56.commonserver.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public final class NumberUtils {
    private NumberUtils() {
    }

    //based on https://stackoverflow.com/a/3758880/14899408
    public static String humanReadableByteCount(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format(Locale.US, "%.1f %cB", bytes / 1000.0, ci.current());
    }
}
