package eenum;

public enum ECsvCharset {
    CHARSET_SJIS,
    CHARSET_UTF8,
    CHARSET_UTF16,
    CHARSET_SHIFT_JIS;
    public static <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int ordinal) {
        E[] enumArray = enumClass.getEnumConstants();
        return enumArray[ordinal];
    }
}
