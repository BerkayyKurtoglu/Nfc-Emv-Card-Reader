package com.berkay.nfcemvcardreader.emvreader.model.enums

/**
 * Represents a currency based on ISO 4217.
 * Each currency is identified by its numeric code, name, formatting digits, and associated countries.
 */
enum class CurrencyEnum(
    val isoCodeNumeric: Int,
    val digits: Digits,
    val displayName: String,
    val countries: Array<CountryCodeEnum>
) : IKeyEnum {

    AED(784, Digits.DIGITS_2, "United Arab Emirates dirham", arrayOf(CountryCodeEnum.AE)),
    AFN(971, Digits.DIGITS_2, "Afghan afghani", arrayOf(CountryCodeEnum.AF)),
    ALL(8, Digits.DIGITS_2, "Albanian lek", arrayOf(CountryCodeEnum.AL)),
    AMD(51, Digits.DIGITS_2, "Armenian dram", arrayOf(CountryCodeEnum.AM)),
    ANG(532,
        Digits.DIGITS_2, "Netherlands Antillean guilder", arrayOf(CountryCodeEnum.CW, CountryCodeEnum.SX)),
    AOA(973, Digits.DIGITS_2, "Angolan kwanza", arrayOf(CountryCodeEnum.AO)),
    ARS(32, Digits.DIGITS_2, "Argentine peso", arrayOf(CountryCodeEnum.AR)),
    AUD(36,
        Digits.DIGITS_2, "Australian dollar", arrayOf(CountryCodeEnum.AU, CountryCodeEnum.CX, CountryCodeEnum.CC, CountryCodeEnum.HM, CountryCodeEnum.KI, CountryCodeEnum.NR, CountryCodeEnum.NF, CountryCodeEnum.TV)),
    AWG(533, Digits.DIGITS_2, "Aruban florin", arrayOf(CountryCodeEnum.AW)),
    AZN(944, Digits.DIGITS_2, "Azerbaijani manat", arrayOf(CountryCodeEnum.AZ)),
    BAM(977, Digits.DIGITS_2, "Bosnia and Herzegovina convertible mark", arrayOf(CountryCodeEnum.BA)),
    BBD(52, Digits.DIGITS_2, "Barbados dollar", arrayOf(CountryCodeEnum.BB)),
    BDT(50, Digits.DIGITS_2, "Bangladeshi taka", arrayOf(CountryCodeEnum.BD)),
    BGN(975, Digits.DIGITS_2, "Bulgarian lev", arrayOf(CountryCodeEnum.BG)),
    BHD(48, Digits.DIGITS_3, "Bahraini dinar", arrayOf(CountryCodeEnum.BH)),
    BIF(108, Digits.DIGITS_0, "Burundian franc", arrayOf(CountryCodeEnum.BI)),
    BMD(60, Digits.DIGITS_2, "Bermudian dollar", arrayOf(CountryCodeEnum.BM)),
    BND(96, Digits.DIGITS_2, "Brunei dollar", arrayOf(CountryCodeEnum.BN, CountryCodeEnum.SG)),
    BOB(68, Digits.DIGITS_2, "Boliviano", arrayOf(CountryCodeEnum.BO)),
    BOV(984, Digits.DIGITS_2, "Bolivian Mvdol (funds code)", arrayOf(CountryCodeEnum.BO)),
    BRL(986, Digits.DIGITS_2, "Brazilian real", arrayOf(CountryCodeEnum.BR)),
    BSD(44, Digits.DIGITS_2, "Bahamian dollar", arrayOf(CountryCodeEnum.BS)),
    BTN(64, Digits.DIGITS_2, "Bhutanese ngultrum", arrayOf(CountryCodeEnum.BT)),
    BWP(72, Digits.DIGITS_2, "Botswana pula", arrayOf(CountryCodeEnum.BW)),
    BYR(974, Digits.DIGITS_0, "Belarusian ruble", arrayOf(CountryCodeEnum.BY)),
    BZD(84, Digits.DIGITS_2, "Belize dollar", arrayOf(CountryCodeEnum.BZ)),
    CAD(124, Digits.DIGITS_2, "Canadian dollar", arrayOf(CountryCodeEnum.CA)),
    CDF(976, Digits.DIGITS_2, "Congolese franc", arrayOf(CountryCodeEnum.CD)),
    CHE(947, Digits.DIGITS_2, "WIR Euro (complementary currency)", arrayOf(CountryCodeEnum.CH)),
    CHF(756, Digits.DIGITS_2, "Swiss franc", arrayOf(CountryCodeEnum.CH, CountryCodeEnum.LI)),
    CHW(948, Digits.DIGITS_2, "WIR Franc (complementary currency)", arrayOf(CountryCodeEnum.CH)),
    CLF(990, Digits.DIGITS_0, "Unidad de Fomento (funds code)", arrayOf(CountryCodeEnum.CL)),
    CLP(152, Digits.DIGITS_0, "Chilean peso", arrayOf(CountryCodeEnum.CL)),
    CNY(156, Digits.DIGITS_2, "Chinese yuan", arrayOf(CountryCodeEnum.CN)),
    COP(170, Digits.DIGITS_2, "Colombian peso", arrayOf(CountryCodeEnum.CO)),
    COU(970, Digits.DIGITS_2, "Unidad de Valor Real", arrayOf(CountryCodeEnum.CO)),
    CRC(188, Digits.DIGITS_2, "Costa Rican colon", arrayOf(CountryCodeEnum.CR)),
    CUC(931, Digits.DIGITS_2, "Cuban convertible peso", arrayOf(CountryCodeEnum.CU)),
    CUP(192, Digits.DIGITS_2, "Cuban peso", arrayOf(CountryCodeEnum.CU)),
    CVE(132, Digits.DIGITS_0, "Cape Verde escudo", arrayOf(CountryCodeEnum.CV)),
    CZK(203, Digits.DIGITS_2, "Czech koruna", arrayOf(CountryCodeEnum.CZ)),
    DJF(262, Digits.DIGITS_0, "Djiboutian franc", arrayOf(CountryCodeEnum.DJ)),
    DKK(208,
        Digits.DIGITS_2, "Danish krone", arrayOf(CountryCodeEnum.DK, CountryCodeEnum.FO, CountryCodeEnum.GL)),
    DOP(214, Digits.DIGITS_2, "Dominican peso", arrayOf(CountryCodeEnum.DO)),
    DZD(12, Digits.DIGITS_2, "Algerian dinar", arrayOf(CountryCodeEnum.DZ)),
    EGP(818, Digits.DIGITS_2, "Egyptian pound", arrayOf(CountryCodeEnum.EG)),
    ERN(232, Digits.DIGITS_2, "Eritrean nakfa", arrayOf(CountryCodeEnum.ER)),
    ETB(230, Digits.DIGITS_2, "Ethiopian birr", arrayOf(CountryCodeEnum.ET)),
    EUR(978, Digits.DIGITS_2, "Euro", arrayOf(
        CountryCodeEnum.AD, CountryCodeEnum.AT, CountryCodeEnum.BE, CountryCodeEnum.CY, CountryCodeEnum.EE,
        CountryCodeEnum.FI, CountryCodeEnum.FR, CountryCodeEnum.DE, CountryCodeEnum.GR, CountryCodeEnum.IE,
        CountryCodeEnum.IT, CountryCodeEnum.LU, CountryCodeEnum.MT, CountryCodeEnum.MC, CountryCodeEnum.ME,
        CountryCodeEnum.NL, CountryCodeEnum.PT, CountryCodeEnum.SM, CountryCodeEnum.SK, CountryCodeEnum.SI,
        CountryCodeEnum.ES, CountryCodeEnum.VA
    )),
    FJD(242, Digits.DIGITS_2, "Fiji dollar", arrayOf(CountryCodeEnum.FJ)),
    FKP(238, Digits.DIGITS_2, "Falkland Islands pound", arrayOf(CountryCodeEnum.FK)),
    GBP(826,
        Digits.DIGITS_2, "Pound sterling", arrayOf(CountryCodeEnum.GB, CountryCodeEnum.IM, CountryCodeEnum.GS, CountryCodeEnum.IO)),
    GEL(981, Digits.DIGITS_2, "Georgian lari", arrayOf(CountryCodeEnum.GE)),
    GHS(936, Digits.DIGITS_2, "Ghanaian cedi", arrayOf(CountryCodeEnum.GH)),
    GIP(292, Digits.DIGITS_2, "Gibraltar pound", arrayOf(CountryCodeEnum.GI)),
    GMD(270, Digits.DIGITS_2, "Gambian dalasi", arrayOf(CountryCodeEnum.GM)),
    GNF(324, Digits.DIGITS_0, "Guinean franc", arrayOf(CountryCodeEnum.GN)),
    GTQ(320, Digits.DIGITS_2, "Guatemalan quetzal", arrayOf(CountryCodeEnum.GT)),
    GYD(328, Digits.DIGITS_2, "Guyanese dollar", arrayOf(CountryCodeEnum.GY)),
    HKD(344, Digits.DIGITS_2, "Hong Kong dollar", arrayOf(CountryCodeEnum.HK, CountryCodeEnum.MO)),
    HNL(340, Digits.DIGITS_2, "Honduran lempira", arrayOf(CountryCodeEnum.HN)),
    HRK(191, Digits.DIGITS_2, "Croatian kuna", arrayOf(CountryCodeEnum.HR)),
    HTG(332, Digits.DIGITS_2, "Haitian gourde", arrayOf(CountryCodeEnum.HT)),
    HUF(348, Digits.DIGITS_2, "Hungarian forint", arrayOf(CountryCodeEnum.HU)),
    IDR(360, Digits.DIGITS_2, "Indonesian rupiah", arrayOf(CountryCodeEnum.ID)),
    ILS(376, Digits.DIGITS_2, "Israeli new shekel", arrayOf(CountryCodeEnum.IL, CountryCodeEnum.PS)),
    INR(356, Digits.DIGITS_2, "Indian rupee", arrayOf(CountryCodeEnum.IN)),
    IQD(368, Digits.DIGITS_3, "Iraqi dinar", arrayOf(CountryCodeEnum.IQ)),
    IRR(364, Digits.DIGITS_0, "Iranian rial", arrayOf(CountryCodeEnum.IR)),
    ISK(352, Digits.DIGITS_0, "Icelandic króna", arrayOf(CountryCodeEnum.IS)),
    JMD(388, Digits.DIGITS_2, "Jamaican dollar", arrayOf(CountryCodeEnum.JM)),
    JOD(400, Digits.DIGITS_3, "Jordanian dinar", arrayOf(CountryCodeEnum.JO)),
    JPY(392, Digits.DIGITS_0, "Japanese yen", arrayOf(CountryCodeEnum.JP)),
    KES(404, Digits.DIGITS_2, "Kenyan shilling", arrayOf(CountryCodeEnum.KE)),
    KGS(417, Digits.DIGITS_2, "Kyrgyzstani som", arrayOf(CountryCodeEnum.KG)),
    KHR(116, Digits.DIGITS_2, "Cambodian riel", arrayOf(CountryCodeEnum.KH)),
    KMF(174, Digits.DIGITS_0, "Comoro franc", arrayOf(CountryCodeEnum.KM)),
    KPW(408, Digits.DIGITS_0, "North Korean won", arrayOf(CountryCodeEnum.KP)),
    KRW(410, Digits.DIGITS_0, "South Korean won", arrayOf(CountryCodeEnum.KR)),
    KWD(414, Digits.DIGITS_3, "Kuwaiti dinar", arrayOf(CountryCodeEnum.KW)),
    KYD(136, Digits.DIGITS_2, "Cayman Islands dollar", arrayOf(CountryCodeEnum.KY)),
    KZT(398, Digits.DIGITS_2, "Kazakhstani tenge", arrayOf(CountryCodeEnum.KZ)),
    LAK(418, Digits.DIGITS_0, "Lao kip", arrayOf(CountryCodeEnum.LA)),
    LBP(422, Digits.DIGITS_0, "Lebanese pound", arrayOf(CountryCodeEnum.LB)),
    LKR(144, Digits.DIGITS_2, "Sri Lankan rupee", arrayOf(CountryCodeEnum.LK)),
    LRD(430, Digits.DIGITS_2, "Liberian dollar", arrayOf(CountryCodeEnum.LR)),
    LSL(426, Digits.DIGITS_2, "Lesotho loti", arrayOf(CountryCodeEnum.LS)),
    LTL(440, Digits.DIGITS_2, "Lithuanian litas", arrayOf(CountryCodeEnum.LT)),
    LVL(428, Digits.DIGITS_2, "Latvian lats", arrayOf(CountryCodeEnum.LV)),
    LYD(434, Digits.DIGITS_3, "Libyan dinar", arrayOf(CountryCodeEnum.LY)),
    MAD(504, Digits.DIGITS_2, "Moroccan dirham", arrayOf(CountryCodeEnum.MA)),
    MDL(498, Digits.DIGITS_2, "Moldovan leu", arrayOf(CountryCodeEnum.MD)),
    MGA(969, Digits.DIGITS_07, "Malagasy ariary", arrayOf(CountryCodeEnum.MG)),
    MKD(807, Digits.DIGITS_0, "Macedonian denar", arrayOf(CountryCodeEnum.MK)),
    MMK(104, Digits.DIGITS_0, "Myanma kyat", arrayOf(CountryCodeEnum.MM)),
    MNT(496, Digits.DIGITS_2, "Mongolian tugrik", arrayOf(CountryCodeEnum.MN)),
    MOP(446, Digits.DIGITS_2, "Macanese pataca", arrayOf(CountryCodeEnum.MO)),
    MRO(478, Digits.DIGITS_07, "Mauritanian ouguiya", arrayOf(CountryCodeEnum.MR)),
    MUR(480, Digits.DIGITS_2, "Mauritian rupee", arrayOf(CountryCodeEnum.MU)),
    MVR(462, Digits.DIGITS_2, "Maldivian rufiyaa", arrayOf(CountryCodeEnum.MV)),
    MWK(454, Digits.DIGITS_2, "Malawian kwacha", arrayOf(CountryCodeEnum.MW)),
    MXN(484, Digits.DIGITS_2, "Mexican peso", arrayOf(CountryCodeEnum.MX)),
    MXV(979, Digits.DIGITS_2, "Mexican Unidad de Inversion (UDI) (funds code)", arrayOf(
        CountryCodeEnum.MX)),
    MYR(458, Digits.DIGITS_2, "Malaysian ringgit", arrayOf(CountryCodeEnum.MY)),
    MZN(943, Digits.DIGITS_2, "Mozambican metical", arrayOf(CountryCodeEnum.MZ)),
    NAD(516, Digits.DIGITS_2, "Namibian dollar", arrayOf(CountryCodeEnum.NA)),
    NGN(566, Digits.DIGITS_2, "Nigerian naira", arrayOf(CountryCodeEnum.NG)),
    NIO(558, Digits.DIGITS_2, "Nicaraguan córdoba", arrayOf(CountryCodeEnum.NI)),
    NOK(578,
        Digits.DIGITS_2, "Norwegian krone", arrayOf(CountryCodeEnum.NO, CountryCodeEnum.SJ, CountryCodeEnum.BV)),
    NPR(524, Digits.DIGITS_2, "Nepalese rupee", arrayOf(CountryCodeEnum.NP)),
    NZD(554,
        Digits.DIGITS_2, "New Zealand dollar", arrayOf(CountryCodeEnum.CK, CountryCodeEnum.NZ, CountryCodeEnum.NU, CountryCodeEnum.PN, CountryCodeEnum.TK)),
    OMR(512, Digits.DIGITS_3, "Omani rial", arrayOf(CountryCodeEnum.OM)),
    PAB(590, Digits.DIGITS_2, "Panamanian balboa", arrayOf(CountryCodeEnum.PA)),
    PEN(604, Digits.DIGITS_2, "Peruvian nuevo sol", arrayOf(CountryCodeEnum.PE)),
    PGK(598, Digits.DIGITS_2, "Papua New Guinean kina", arrayOf(CountryCodeEnum.PG)),
    PHP(608, Digits.DIGITS_2, "Philippine peso", arrayOf(CountryCodeEnum.PH)),
    PKR(586, Digits.DIGITS_2, "Pakistani rupee", arrayOf(CountryCodeEnum.PK)),
    PLN(985, Digits.DIGITS_2, "Polish złoty", arrayOf(CountryCodeEnum.PL)),
    PYG(600, Digits.DIGITS_0, "Paraguayan guaraní", arrayOf(CountryCodeEnum.PY)),
    QAR(634, Digits.DIGITS_2, "Qatari riyal", arrayOf(CountryCodeEnum.QA)),
    RON(946, Digits.DIGITS_2, "Romanian new leu", arrayOf(CountryCodeEnum.RO)),
    RSD(941, Digits.DIGITS_2, "Serbian dinar", arrayOf(CountryCodeEnum.RS)),
    RUB(643, Digits.DIGITS_2, "Russian rouble", arrayOf(CountryCodeEnum.RU)),
    RWF(646, Digits.DIGITS_0, "Rwandan franc", arrayOf(CountryCodeEnum.RW)),
    SAR(682, Digits.DIGITS_2, "Saudi riyal", arrayOf(CountryCodeEnum.SA)),
    SBD(90, Digits.DIGITS_2, "Solomon Islands dollar", arrayOf(CountryCodeEnum.SB)),
    SCR(690, Digits.DIGITS_2, "Seychelles rupee", arrayOf(CountryCodeEnum.SC)),
    SDG(938, Digits.DIGITS_2, "Sudanese pound", arrayOf(CountryCodeEnum.SD)),
    SEK(752, Digits.DIGITS_2, "Swedish krona/kronor", arrayOf(CountryCodeEnum.SE)),
    SGD(702, Digits.DIGITS_2, "Singapore dollar", arrayOf(CountryCodeEnum.SG, CountryCodeEnum.BN)),
    SHP(654, Digits.DIGITS_2, "Saint Helena pound", arrayOf(CountryCodeEnum.SH)),
    SLL(694, Digits.DIGITS_0, "Sierra Leonean leone", arrayOf(CountryCodeEnum.SL)),
    SOS(706, Digits.DIGITS_2, "Somali shilling", arrayOf(CountryCodeEnum.SO)),
    SRD(968, Digits.DIGITS_2, "Surinamese dollar", arrayOf(CountryCodeEnum.SR)),
    SSP(728, Digits.DIGITS_2, "South Sudanese pound", arrayOf(CountryCodeEnum.SS)),
    STD(678, Digits.DIGITS_0, "São Tomé and Príncipe dobra", arrayOf(CountryCodeEnum.ST)),
    SYP(760, Digits.DIGITS_2, "Syrian pound", arrayOf(CountryCodeEnum.SY)),
    SZL(748, Digits.DIGITS_2, "Swazi lilangeni", arrayOf(CountryCodeEnum.SZ)),
    THB(764, Digits.DIGITS_2, "Thai baht", arrayOf(CountryCodeEnum.TH)),
    TJS(972, Digits.DIGITS_2, "Tajikistani somoni", arrayOf(CountryCodeEnum.TJ)),
    TMT(934, Digits.DIGITS_2, "Turkmenistani manat", arrayOf(CountryCodeEnum.TM)),
    TND(788, Digits.DIGITS_3, "Tunisian dinar", arrayOf(CountryCodeEnum.TN)),
    TOP(776, Digits.DIGITS_2, "Tongan paʻanga", arrayOf(CountryCodeEnum.TO)),
    TRY(949, Digits.DIGITS_2, "Turkish lira", arrayOf(CountryCodeEnum.TR)),
    TTD(780, Digits.DIGITS_2, "Trinidad and Tobago dollar", arrayOf(CountryCodeEnum.TT)),
    TWD(901, Digits.DIGITS_2, "New Taiwan dollar", arrayOf(CountryCodeEnum.TW)),
    TZS(834, Digits.DIGITS_2, "Tanzanian shilling", arrayOf(CountryCodeEnum.TZ)),
    UAH(980, Digits.DIGITS_2, "Ukrainian hryvnia", arrayOf(CountryCodeEnum.UA)),
    UGX(800, Digits.DIGITS_2, "Ugandan shilling", arrayOf(CountryCodeEnum.UG)),
    USD(840, Digits.DIGITS_2, "United States dollar", arrayOf(
        CountryCodeEnum.AS, CountryCodeEnum.BB, CountryCodeEnum.BM, CountryCodeEnum.IO,
        CountryCodeEnum.VG, CountryCodeEnum.BQ, CountryCodeEnum.EC, CountryCodeEnum.SV,
        CountryCodeEnum.GU, CountryCodeEnum.HT, CountryCodeEnum.MH, CountryCodeEnum.FM,
        CountryCodeEnum.MP, CountryCodeEnum.PW, CountryCodeEnum.PA, CountryCodeEnum.PR,
        CountryCodeEnum.TL, CountryCodeEnum.TC, CountryCodeEnum.US, CountryCodeEnum.VI,
        CountryCodeEnum.ZW
    )),
    USN(997, Digits.DIGITS_2, "United States dollar (next day) (funds code)", arrayOf(
        CountryCodeEnum.US)),
    USS(998, Digits.DIGITS_2, "United States dollar (same day) (funds code)", arrayOf(
        CountryCodeEnum.US)),
    UYI(940, Digits.DIGITS_0, "Uruguay Peso en Unidades Indexadas (URUIURUI) (funds code)", arrayOf(
        CountryCodeEnum.UY)),
    UYU(858, Digits.DIGITS_2, "Uruguayan peso", arrayOf(CountryCodeEnum.UY)),
    UZS(860, Digits.DIGITS_2, "Uzbekistan som", arrayOf(CountryCodeEnum.UZ)),
    VEF(937, Digits.DIGITS_2, "Venezuelan bolívar fuerte", arrayOf(CountryCodeEnum.VE)),
    VND(704, Digits.DIGITS_0, "Vietnamese dong", arrayOf(CountryCodeEnum.VN)),
    VUV(548, Digits.DIGITS_0, "Vanuatu vatu", arrayOf(CountryCodeEnum.VU)),
    WST(882, Digits.DIGITS_2, "Samoan tala", arrayOf(CountryCodeEnum.WS)),
    XAF(950, Digits.DIGITS_0, "CFA franc BEAC", arrayOf(
        CountryCodeEnum.CM, CountryCodeEnum.CF, CountryCodeEnum.CD,
        CountryCodeEnum.TD, CountryCodeEnum.GQ, CountryCodeEnum.GA
    )),
    XAG(961, Digits.DIGITS_NO, "Silver (one troy ounce)", emptyArray()),
    XAU(959, Digits.DIGITS_NO, "Gold (one troy ounce)", emptyArray()),
    XBA(955, Digits.DIGITS_NO, "European Composite Unit (EURCO) (bond market unit)", emptyArray()),
    XBB(956, Digits.DIGITS_NO, "European Monetary Unit (E.M.U.-6) (bond market unit)", emptyArray()),
    XBC(957,
        Digits.DIGITS_NO, "European Unit of Account 9 (E.U.A.-9) (bond market unit)", emptyArray()),
    XBD(958,
        Digits.DIGITS_NO, "European Unit of Account 17 (E.U.A.-17) (bond market unit)", emptyArray()),
    XCD(951, Digits.DIGITS_2, "East Caribbean dollar", arrayOf(
        CountryCodeEnum.AI, CountryCodeEnum.AG, CountryCodeEnum.DM,
        CountryCodeEnum.GD, CountryCodeEnum.MS, CountryCodeEnum.KN,
        CountryCodeEnum.LC, CountryCodeEnum.VC
    )),
    XDR(960, Digits.DIGITS_NO, "Special drawing rights  International Monetary Fund", emptyArray()),
    XFU(-1, Digits.DIGITS_NO, "UIC franc (special settlement currency)", emptyArray()),
    XOF(952, Digits.DIGITS_0, "CFA franc BCEAO", arrayOf(
        CountryCodeEnum.BJ, CountryCodeEnum.BF, CountryCodeEnum.CI,
        CountryCodeEnum.GW, CountryCodeEnum.ML, CountryCodeEnum.NE,
        CountryCodeEnum.SN, CountryCodeEnum.TG
    )),
    XPD(964, Digits.DIGITS_NO, "Palladium (one troy ounce)", emptyArray()),
    XPF(953, Digits.DIGITS_0, "CFP franc", arrayOf(
        CountryCodeEnum.PF, CountryCodeEnum.NC, CountryCodeEnum.WF
    )),
    XPT(962, Digits.DIGITS_NO, "Platinum (one troy ounce)", emptyArray()),
    XTS(963, Digits.DIGITS_NO, "Code reserved for testing purposes", emptyArray()),
    XXX(0, Digits.DIGITS_NO, "No currency", emptyArray()),
    YER(886, Digits.DIGITS_2, "Yemeni rial", arrayOf(CountryCodeEnum.YE)),
    ZAR(710, Digits.DIGITS_2, "South African rand", arrayOf(CountryCodeEnum.ZA)),
    ZMK(894, Digits.DIGITS_2, "Zambian kwacha", arrayOf(CountryCodeEnum.ZM));

    override fun getKey(): Int = isoCodeNumeric

//    fun format(amount: Long): String {
//        val formatted = when (digits) {
//            Digits.DIGITS_0 -> amount.toString()
//            Digits.DIGITS_2 -> "%d.%02d".format(amount / 100, amount % 100)
//            Digits.DIGITS_3 -> "%d.%03d".format(amount / 1000, amount % 1000)
//            Digits.DIGITS_07 -> amount.toString()
//            Digits.DIGITS_NO -> amount.toString()
//        }
//        return "$code $formatted"
//    }

    companion object {

        /**
         * Finds a currency by its ISO 4217 alphabetic code (e.g. "USD").
         */
        fun find(code: String): CurrencyEnum? {
            return entries.firstOrNull { it.name == code.uppercase() }
        }

        /**
         * Finds a currency for the given country.
         * Returns null if multiple or no currencies are found.
         */
        fun find(country: CountryCodeEnum?): CurrencyEnum? {
            if (country == null) return null

            return when (country) {
                CountryCodeEnum.BO -> BOB
                CountryCodeEnum.CH -> CHF
                CountryCodeEnum.CL -> CLP
                CountryCodeEnum.MX -> MXN
                CountryCodeEnum.US -> USD
                CountryCodeEnum.UY -> UYU
                else -> {
                    val matches = entries.filter { it.countries.contains(country) }
                    if (matches.size == 1) matches.first() else null
                }
            }
        }

        /**
         * Finds a currency for the given country, or returns the provided default.
         */
        fun find(country: CountryCodeEnum?, default: CurrencyEnum): CurrencyEnum {
            return find(country) ?: default
        }
    }
}

/**
 * Represents how many decimal digits a currency uses when formatting.
 */
enum class Digits {
    DIGITS_0,
    DIGITS_2,
    DIGITS_3,
    DIGITS_07,
    DIGITS_NO
}
