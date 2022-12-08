package hepper;

public enum JapaneseMode {
    NONE,
    ZENKAKU_NOMI,
    HANKAKU_NOMI,
    ZENKAKU_KATAKANA_NOMI,
    HANKAKU_KATAKANA_NOMI,
    HANKAKU_EISUU_NOMI,
    HANKAKU_EISUU_DAIMOJI_NOMI,
    HANKAKU_EISUU_KOMOJI_NOMI;



    public static <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int ordinal) {
        E[] enumArray = enumClass.getEnumConstants();
        return enumArray[ordinal];
    }
}
