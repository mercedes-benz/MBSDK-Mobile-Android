package com.daimler.mbprotokit.dto.car.headunit

enum class LanguageState(val id: Int) {
    GERMAN(0),
    ENGLISH_IMP(1),
    FRENCH(2),
    ITALIAN(3),
    SPANISH(4),
    JAPANESE(5),
    ENGLISH_MET(6),
    DUTCH(7),
    DANISH(8),
    SWEDISH(9),
    TURKISH(10),
    PORTUGUESE(11),
    RUSSIAN(12),
    ARABIC(13),
    CHINESE(14),
    ENGLISH_AM(15),
    TRAD_CHINESE(16),
    KOREAN(17),
    FINNISH(18),
    POLISH(19),
    CZESH(20),
    PORTUGUESE_BRAZIL(21),
    NORWEGIAN(22),
    THAI(23),
    INDONESIAN(24),
    BULGARIAN(25),
    SLOVAKIAN(26),
    CROATIAN(27),
    SERBIAN(28),
    HUNGARIAN(29),
    UKRAINIAN(30),
    MALAYAN(31),
    VIETNAMESE(32),
    ROMANIAN(33);

    companion object {
        fun map(id: Int?) = values().find {
            it.id == id
        }

        fun map(): (Int?) -> LanguageState? = { map(it) }
    }
}
