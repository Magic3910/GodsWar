package com.magical.Utility;

import java.util.HashMap;
import java.util.Map;

public class Hangul {

    private static final char HANGUL_SYLLABLES_BEGIN = 0xAC00;
    private static final char HANGUL_SYLLABLES_END = 0xD7A3;

    private static final char[] HANGUL_CHOSEONG = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};
    private static final char[] HANGUL_JUNGSEONG = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163};
    private static final char[] HANGUL_JONGSEONG = {0x0000, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

    public static final int HANGUL_JUNGSEONG_SIZE = HANGUL_JUNGSEONG.length;	// 21
    public static final int HANGUL_JONGSEONG_SIZE = HANGUL_JONGSEONG.length;	// 28

    private static final Map<Character, Integer> HANGUL_CHOSEONG_CODE_TABLE = new HashMap<>();
    private static final Map<Character, Integer> HANGUL_JUNGSEONG_CODE_TABLE = new HashMap<>();
    private static final Map<Character, Integer> HANGUL_JONGSEONG_CODE_TABLE = new HashMap<>();

    static {
        for (int i = 0; i < HANGUL_CHOSEONG.length; i++) {
            HANGUL_CHOSEONG_CODE_TABLE.put(HANGUL_CHOSEONG[i], i);
        }
        for (int i = 0; i < HANGUL_JUNGSEONG.length; i++) {
            HANGUL_JUNGSEONG_CODE_TABLE.put(HANGUL_JUNGSEONG[i], i);
        }
        for (int i = 0; i < HANGUL_JONGSEONG.length; i++) {
            HANGUL_JONGSEONG_CODE_TABLE.put(HANGUL_JONGSEONG[i], i);
        }
    }

    public static boolean isHangulSyllables(char ch) {
        return ch >= HANGUL_SYLLABLES_BEGIN && ch <= HANGUL_SYLLABLES_END;
    }

    public static char getJongseong(char ch) {
        if (!isHangulSyllables(ch)) {
            throw new IllegalArgumentException("한글이 아닌 것 같습니다. (" + ch + ")");
        }
        int hCode = ((ch - HANGUL_SYLLABLES_BEGIN) % (HANGUL_JUNGSEONG_SIZE * HANGUL_JONGSEONG_SIZE)) % HANGUL_JONGSEONG_SIZE;
        return HANGUL_JONGSEONG[hCode];
    }

    public static boolean hasJongseong(char ch) {
        return getJongseong(ch) != HANGUL_JONGSEONG[0];
    }


    public static String getJosa(char ch, String aux1, String aux2) {
        return hasJongseong(ch) ? aux1 : aux2;
    }
}
