package com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag

import com.berkay.nfcemvcardreader.emvreader.enums.TagTypeEnum
import com.berkay.nfcemvcardreader.emvreader.enums.TagValueTypeEnum
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.ByteArrayWrapper
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils

enum class EmvTag(
    idHex: String,
    private val tagValueType: TagValueTypeEnum,
    private val tagName: String,
    private val tagDescription: String
) : ITag {
    CARD_PRODUCTION_LIFECYCLE_DATA("9f7f", TagValueTypeEnum.BINARY, "Card Production Life Cycle Data", ""),

    UNIVERSAL_TAG_FOR_OID("06", TagValueTypeEnum.BINARY, "Object Identifier (OID)", "Universal tag for OID"),
    COUNTRY_CODE("41", TagValueTypeEnum.NUMERIC, "Country Code", "Country code (encoding specified in ISO 3166-1) and optional national data"),
    ISSUER_IDENTIFICATION_NUMBER("42", TagValueTypeEnum.NUMERIC, "Issuer Identification Number (IIN)", "The number that identifies the major industry and the card issuer and that forms the first part of the Primary Account Number (PAN)"),
    AID_CARD("4f", TagValueTypeEnum.BINARY, "Application Identifier (AID) - card", "Identifies the application as described in ISO/IEC 7816-5"),
    APPLICATION_LABEL("50", TagValueTypeEnum.TEXT, "Application Label", "Mnemonic associated with the AID according to ISO/IEC 7816-5"),
    PATH("51", TagValueTypeEnum.BINARY, "File reference data element", "ISO-7816 Path"),
    COMMAND_APDU("52", TagValueTypeEnum.BINARY, "Command APDU", ""),
    DISCRETIONARY_DATA_OR_TEMPLATE("53", TagValueTypeEnum.BINARY, "Discretionary data (or template)", ""),
    APPLICATION_TEMPLATE("61", TagValueTypeEnum.BINARY, "Application Template", "Contains one or more data objects relevant to an application directory entry according to ISO/IEC 7816-5"),
    FCI_TEMPLATE("6f", TagValueTypeEnum.BINARY, "File Control Information (FCI) Template", "Set of file control parameters and file management data (according to ISO/IEC 7816-4)"),
    DD_TEMPLATE("73", TagValueTypeEnum.BINARY, "Directory Discretionary Template", "Issuer discretionary part of the directory according to ISO/IEC 7816-5"),
    DEDICATED_FILE_NAME("84", TagValueTypeEnum.BINARY, "Dedicated File (DF) Name", "Identifies the name of the DF as described in ISO/IEC 7816-4"),
    SFI("88", TagValueTypeEnum.BINARY, "Short File Identifier (SFI)", "Identifies the SFI to be used in the commands related to a given AEF or DDF. The SFI data object is a binary field with the three high order bits set to zero"),
    FCI_PROPRIETARY_TEMPLATE("a5", TagValueTypeEnum.BINARY, "File Control Information (FCI) Proprietary Template", "Identifies the data object proprietary to this specification in the FCI template according to ISO/IEC 7816-4"),
    ISSUER_URL("5f50", TagValueTypeEnum.TEXT, "Issuer URL", "The URL provides the location of the Issuer’s Library Server on the Internet"),
    TRACK_2_EQV_DATA("57", TagValueTypeEnum.BINARY, "Track 2 Equivalent Data", "Contains the data elements of track 2 according to ISO/IEC 7813, excluding start sentinel, end sentinel, and Longitudinal Redundancy Check (LRC)"),
    PAN("5a", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN)", "Valid cardholder account number"),
    RECORD_TEMPLATE("70", TagValueTypeEnum.BINARY, "Record Template (EMV Proprietary)", "Template proprietary to the EMV specification"),
    ISSUER_SCRIPT_TEMPLATE_1("71", TagValueTypeEnum.BINARY, "Issuer Script Template 1", "Contains proprietary issuer data for transmission to the ICC before the second GENERATE AC command"),
    ISSUER_SCRIPT_TEMPLATE_2("72", TagValueTypeEnum.BINARY, "Issuer Script Template 2", "Contains proprietary issuer data for transmission to the ICC after the second GENERATE AC command"),
    RESPONSE_MESSAGE_TEMPLATE_1("80", TagValueTypeEnum.BINARY, "Response Message Template Format 1", "Contains the data objects (without tags and lengths) returned by the ICC in response to a command"),
    AMOUNT_AUTHORISED_BINARY("81", TagValueTypeEnum.BINARY, "Amount, Authorised (Binary)", "Authorised amount of the transaction (excluding adjustments)"),
    APPLICATION_INTERCHANGE_PROFILE("82", TagValueTypeEnum.BINARY, "Application Interchange Profile", "Indicates the capabilities of the card to support specific functions in the application"),
    COMMAND_TEMPLATE("83", TagValueTypeEnum.BINARY, "Command Template", "Identifies the data field of a command message"),
    ISSUER_SCRIPT_COMMAND("86", TagValueTypeEnum.BINARY, "Issuer Script Command", "Contains a command for transmission to the ICC"),
    APPLICATION_PRIORITY_INDICATOR("87", TagValueTypeEnum.BINARY, "Application Priority Indicator", "Indicates the priority of a given application or group of applications in a directory"),
    AUTHORISATION_CODE("89", TagValueTypeEnum.BINARY, "Authorisation Code", "Value generated by the authorisation authority for an approved transaction"),
    AUTHORISATION_RESPONSE_CODE("8a", TagValueTypeEnum.TEXT, "Authorisation Response Code", "Code that defines the disposition of a message"),
    CDOL1("8c", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 1 (CDOL1)", "List of data objects (tag and length) to be passed to the ICC in the first GENERATE AC command"),
    CDOL2("8d", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 2 (CDOL2)", "List of data objects (tag and length) to be passed to the ICC in the second GENERATE AC command"),
    CVM_LIST("8e", TagValueTypeEnum.BINARY, "Cardholder Verification Method (CVM) List", "Identifies a method of verification of the cardholder supported by the application"),
    CA_PUBLIC_KEY_INDEX_CARD("8f", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - card", "Identifies the certification authority’s public key in conjunction with the RID"),
    ISSUER_PUBLIC_KEY_CERT("90", TagValueTypeEnum.BINARY, "Issuer Public Key Certificate", "Issuer public key certified by a certification authority"),
    ISSUER_AUTHENTICATION_DATA("91", TagValueTypeEnum.BINARY, "Issuer Authentication Data", "Data sent to the ICC for online issuer authentication"),
    ISSUER_PUBLIC_KEY_REMAINDER("92", TagValueTypeEnum.BINARY, "Issuer Public Key Remainder", "Remaining digits of the Issuer Public Key Modulus"),
    SIGNED_STATIC_APP_DATA("93", TagValueTypeEnum.BINARY, "Signed Static Application Data", "Digital signature on critical application parameters for SDA"),
    APPLICATION_FILE_LOCATOR("94", TagValueTypeEnum.BINARY, "Application File Locator (AFL)", "Indicates the location (SFI, range of records) of the AEFs related to a given application"),
    TERMINAL_VERIFICATION_RESULTS("95", TagValueTypeEnum.BINARY, "Terminal Verification Results (TVR)", "Status of the different functions as seen from the terminal"),
    TDOL("97", TagValueTypeEnum.BINARY, "Transaction Certificate Data Object List (TDOL)", "List of data objects (tag and length) to be used by the terminal in generating the TC Hash Value"),
    TC_HASH_VALUE("98", TagValueTypeEnum.BINARY, "Transaction Certificate (TC) Hash Value", "Result of a hash function specified in Book 2, Annex B3.1"),
    TRANSACTION_PIN_DATA("99", TagValueTypeEnum.BINARY, "Transaction Personal Identification Number (PIN) Data", "Data entered by the cardholder for the purpose of the PIN verification"),
    TRANSACTION_DATE("9a", TagValueTypeEnum.NUMERIC, "Transaction Date", "Local date that the transaction was authorised"),
    TRANSACTION_STATUS_INFORMATION("9b", TagValueTypeEnum.BINARY, "Transaction Status Information", "Indicates the functions performed in a transaction"),
    TRANSACTION_TYPE("9c", TagValueTypeEnum.NUMERIC, "Transaction Type", "Indicates the type of financial transaction, represented by the first two digits of ISO 8583:1987 Processing Code"),
    DDF_NAME("9d", TagValueTypeEnum.BINARY, "Directory Definition File (DDF) Name", "Identifies the name of a DF associated with a directory"),
    CARDHOLDER_NAME("5f20", TagValueTypeEnum.TEXT, "Cardholder Name", "Indicates cardholder name according to ISO 7813"),
    APP_EXPIRATION_DATE("5f24", TagValueTypeEnum.NUMERIC, "Application Expiration Date", "Date after which application expires"),
    APP_EFFECTIVE_DATE("5f25", TagValueTypeEnum.NUMERIC, "Application Effective Date", "Date from which the application may be used"),
    ISSUER_COUNTRY_CODE("5f28", TagValueTypeEnum.NUMERIC, "Issuer Country Code", "Indicates the country of the issuer according to ISO 3166"),
    TRANSACTION_CURRENCY_CODE("5f2a", TagValueTypeEnum.TEXT, "Transaction Currency Code", "Indicates the currency code of the transaction according to ISO 4217"),
    LANGUAGE_PREFERENCE("5f2d", TagValueTypeEnum.TEXT, "Language Preference", "1–4 languages stored in order of preference, each represented by 2 alphabetical characters according to ISO 639"),
    SERVICE_CODE("5f30", TagValueTypeEnum.NUMERIC, "Service Code", "Service code as defined in ISO/IEC 7813 for track 1 and track 2"),
    PAN_SEQUENCE_NUMBER("5f34", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN) Sequence Number", "Identifies and differentiates cards with the same PAN"),
    TRANSACTION_CURRENCY_EXP("5f36", TagValueTypeEnum.NUMERIC, "Transaction Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount represented according to ISO 4217"),
    IBAN("5f53", TagValueTypeEnum.BINARY, "International Bank Account Number (IBAN)", "Uniquely identifies the account of a customer at a financial institution as defined in ISO 13616"),
    BANK_IDENTIFIER_CODE("5f54", TagValueTypeEnum.MIXED, "Bank Identifier Code (BIC)", "Uniquely identifies a bank as defined in ISO 9362"),
    ISSUER_COUNTRY_CODE_ALPHA2("5f55", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha2 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 2 character alphabetic code)"),
    ISSUER_COUNTRY_CODE_ALPHA3("5f56", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha3 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 3 character alphabetic code)"),
    ACQUIRER_IDENTIFIER("9f01", TagValueTypeEnum.NUMERIC, "Acquirer Identifier", "Uniquely identifies the acquirer within each payment system"),
    AMOUNT_AUTHORISED_NUMERIC("9f02", TagValueTypeEnum.NUMERIC, "Amount, Authorised (Numeric)", "Authorised amount of the transaction (excluding adjustments)"),
    AMOUNT_OTHER_NUMERIC("9f03", TagValueTypeEnum.NUMERIC, "Amount, Other (Numeric)", "Secondary amount associated with the transaction representing a cashback amount"),
    AMOUNT_OTHER_BINARY("9f04", TagValueTypeEnum.NUMERIC, "Amount, Other (Binary)", "Secondary amount associated with the transaction representing a cashback amount"),
    APP_DISCRETIONARY_DATA("9f05", TagValueTypeEnum.BINARY, "Application Discretionary Data", "Issuer or payment system specified data relating to the application"),
    AID_TERMINAL("9f06", TagValueTypeEnum.BINARY, "Application Identifier (AID) - terminal", "Identifies the application as described in ISO/IEC 7816-5"),
    APP_USAGE_CONTROL("9f07", TagValueTypeEnum.BINARY, "Application Usage Control", "Indicates issuer’s specified restrictions on the geographic usage and services allowed for the application"),
    APP_VERSION_NUMBER_CARD("9f08", TagValueTypeEnum.BINARY, "Application Version Number - card", "Version number assigned by the payment system for the application"),
    APP_VERSION_NUMBER_TERMINAL("9f09", TagValueTypeEnum.BINARY, "Application Version Number - terminal", "Version number assigned by the payment system for the application"),
    CARDHOLDER_NAME_EXTENDED("9f0b", TagValueTypeEnum.TEXT, "Cardholder Name Extended", "Indicates the whole cardholder name when greater than 26 characters using the same coding convention as in ISO 7813"),
    ISSUER_ACTION_CODE_DEFAULT("9f0d", TagValueTypeEnum.BINARY, "Issuer Action Code - Default", "Specifies the issuer’s conditions that cause a transaction to be rejected if it might have been approved online, but the terminal is unable to process the transaction online"),
    ISSUER_ACTION_CODE_DENIAL("9f0e", TagValueTypeEnum.BINARY, "Issuer Action Code - Denial", "Specifies the issuer’s conditions that cause the denial of a transaction without attempt to go online"),
    ISSUER_ACTION_CODE_ONLINE("9f0f", TagValueTypeEnum.BINARY, "Issuer Action Code - Online", "Specifies the issuer’s conditions that cause a transaction to be transmitted online"),
    ISSUER_APPLICATION_DATA("9f10", TagValueTypeEnum.BINARY, "Issuer Application Data", "Contains proprietary application data for transmission to the issuer in an online transaction"),
    ISSUER_CODE_TABLE_INDEX("9f11", TagValueTypeEnum.NUMERIC, "Issuer Code Table Index", "Indicates the code table according to ISO/IEC 8859 for displaying the Application Preferred Name"),
    APPLICATION_PREFERRED_NAME("9f12", TagValueTypeEnum.TEXT, "Application Preferred Name", "Preferred mnemonic associated with the AID"),
    LAST_ONLINE_ATC_REGISTER("9f13", TagValueTypeEnum.BINARY, "Last Online Application Transaction Counter (ATC) Register", "ATC value of the last transaction that went online"),
    LOWER_CONSEC_OFFLINE_LIMIT("9f14", TagValueTypeEnum.BINARY, "Lower Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal with online capability"),
    MERCHANT_CATEGORY_CODE("9f15", TagValueTypeEnum.NUMERIC, "Merchant Category Code", "Classifies the type of business being done by the merchant, represented according to ISO 8583:1993 for Card Acceptor Business Code"),
    MERCHANT_IDENTIFIER("9f16", TagValueTypeEnum.TEXT, "Merchant Identifier", "When concatenated with the Acquirer Identifier, uniquely identifies a given merchant"),
    PIN_TRY_COUNTER("9f17", TagValueTypeEnum.BINARY, "Personal Identification Number (PIN) Try Counter", "Number of PIN tries remaining"),
    ISSUER_SCRIPT_IDENTIFIER("9f18", TagValueTypeEnum.BINARY, "Issuer Script Identifier", "Identification of the Issuer Script"),
    TERMINAL_COUNTRY_CODE("9f1a", TagValueTypeEnum.TEXT, "Terminal Country Code", "Indicates the country of the terminal, represented according to ISO 3166"),
    TERMINAL_FLOOR_LIMIT("9f1b", TagValueTypeEnum.BINARY, "Terminal Floor Limit", "Indicates the floor limit in the terminal in conjunction with the AID"),
    TERMINAL_IDENTIFICATION("9f1c", TagValueTypeEnum.TEXT, "Terminal Identification", "Designates the unique location of a terminal at a merchant"),
    TERMINAL_RISK_MANAGEMENT_DATA("9f1d", TagValueTypeEnum.BINARY, "Terminal Risk Management Data", "Application-specific value used by the card for risk management purposes"),
    INTERFACE_DEVICE_SERIAL_NUMBER("9f1e", TagValueTypeEnum.TEXT, "Interface Device (IFD) Serial Number", "Unique and permanent serial number assigned to the IFD by the manufacturer"),
    TRACK1_DISCRETIONARY_DATA("9f1f", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 1 Discretionary Data", "Discretionary part of track 1 according to ISO/IEC 7813"),
    TRACK2_DISCRETIONARY_DATA("9f20", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 2 Discretionary Data", "Discretionary part of track 2 according to ISO/IEC 7813"),
    TRANSACTION_TIME("9f21", TagValueTypeEnum.NUMERIC, "Transaction Time (HHMMSS)", "Local time that the transaction was authorised"),
    CA_PUBLIC_KEY_INDEX_TERMINAL("9f22", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - Terminal", "Identifies the certification authority’s public key in conjunction with the RID"),
    UPPER_CONSEC_OFFLINE_LIMIT("9f23", TagValueTypeEnum.BINARY, "Upper Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal without online capability"),
    APP_CRYPTOGRAM("9f26", TagValueTypeEnum.BINARY, "Application Cryptogram", "Cryptogram returned by the ICC in response of the GENERATE AC command"),
    CRYPTOGRAM_INFORMATION_DATA("9f27", TagValueTypeEnum.BINARY, "Cryptogram Information Data", "Indicates the type of cryptogram and the actions to be performed by the terminal"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_CERT("9f2d", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Certificate", "ICC PIN Encipherment Public Key certified by the issuer"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_EXP("9f2e", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Exponent", "ICC PIN Encipherment Public Key Exponent used for PIN encipherment"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_REM("9f2f", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Remainder", "Remaining digits of the ICC PIN Encipherment Public Key Modulus"),
    ISSUER_PUBLIC_KEY_EXP("9f32", TagValueTypeEnum.BINARY, "Issuer Public Key Exponent", "Issuer public key exponent used for the verification of the Signed Static Application Data and the ICC Public Key Certificate"),
    TERMINAL_CAPABILITIES("9f33", TagValueTypeEnum.BINARY, "Terminal Capabilities", "Indicates the card data input, CVM, and security capabilities of the terminal"),
    CVM_RESULTS("9f34", TagValueTypeEnum.BINARY, "Cardholder Verification (CVM) Results", "Indicates the results of the last CVM performed"),
    TERMINAL_TYPE("9f35", TagValueTypeEnum.NUMERIC, "Terminal Type", "Indicates the environment of the terminal, its communications capability, and its operational control"),
    APP_TRANSACTION_COUNTER("9f36", TagValueTypeEnum.BINARY, "Application Transaction Counter (ATC)", "Counter maintained by the application in the ICC (incrementing the ATC is managed by the ICC)"),
    UNPREDICTABLE_NUMBER("9f37", TagValueTypeEnum.BINARY, "Unpredictable Number", "Value to provide variability and uniqueness to the generation of a cryptogram"),
    PDOL("9f38", TagValueTypeEnum.DOL, "Processing Options Data Object List (PDOL)", "Contains a list of terminal resident data objects (tags and lengths) needed by the ICC in processing the GET PROCESSING OPTIONS command"),
    POINT_OF_SERVICE_ENTRY_MODE("9f39", TagValueTypeEnum.NUMERIC, "Point-of-Service (POS) Entry Mode", "Indicates the method by which the PAN was entered, according to the first two digits of the ISO 8583:1987 POS Entry Mode"),
    AMOUNT_REFERENCE_CURRENCY("9f3a", TagValueTypeEnum.BINARY, "Amount, Reference Currency", "Authorised amount expressed in the reference currency"),
    APP_REFERENCE_CURRENCY("9f3b", TagValueTypeEnum.NUMERIC, "Application Reference Currency", "1–4 currency codes used between the terminal and the ICC when the Transaction Currency Code is different from the Application Currency Code; each code is 3 digits according to ISO 4217"),
    TRANSACTION_REFERENCE_CURRENCY_CODE("9f3c", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Code", "Code defining the common currency used by the terminal in case the Transaction Currency Code is different from the Application Currency Code"),
    TRANSACTION_REFERENCE_CURRENCY_EXP("9f3d", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount, with the Transaction Reference Currency Code represented according to ISO 4217"),
    ADDITIONAL_TERMINAL_CAPABILITIES("9f40", TagValueTypeEnum.BINARY, "Additional Terminal Capabilities", "Indicates the data input and output capabilities of the terminal"),
    TRANSACTION_SEQUENCE_COUNTER("9f41", TagValueTypeEnum.NUMERIC, "Transaction Sequence Counter", "Counter maintained by the terminal that is incremented by one for each transaction"),
    APPLICATION_CURRENCY_CODE("9f42", TagValueTypeEnum.NUMERIC, "Application Currency Code", "Indicates the currency in which the account is managed according to ISO 4217"),
    APP_REFERENCE_CURRECY_EXPONENT("9f43", TagValueTypeEnum.NUMERIC, "Application Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount, for each of the 1–4 reference currencies represented according to ISO 4217"),
    APP_CURRENCY_EXPONENT("9f44", TagValueTypeEnum.NUMERIC, "Application Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount represented according to ISO 4217"),
    DATA_AUTHENTICATION_CODE("9f45", TagValueTypeEnum.BINARY, "Data Authentication Code", "An issuer assigned value that is retained by the terminal during the verification process of the Signed Static Application Data"),
    ICC_PUBLIC_KEY_CERT("9f46", TagValueTypeEnum.BINARY, "ICC Public Key Certificate", "ICC Public Key certified by the issuer"),
    ICC_PUBLIC_KEY_EXP("9f47", TagValueTypeEnum.BINARY, "ICC Public Key Exponent", "ICC Public Key Exponent used for the verification of the Signed Dynamic Application Data"),
    ICC_PUBLIC_KEY_REMAINDER("9f48", TagValueTypeEnum.BINARY, "ICC Public Key Remainder", "Remaining digits of the ICC Public Key Modulus"),
    DDOL("9f49", TagValueTypeEnum.DOL, "Dynamic Data Authentication Data Object List (DDOL)", "List of data objects (tag and length) to be passed to the ICC in the INTERNAL AUTHENTICATE command"),
    SDA_TAG_LIST("9f4a", TagValueTypeEnum.BINARY, "Static Data Authentication Tag List", "List of tags of primitive data objects defined in this specification whose value fields are to be included in the Signed Static or Dynamic Application Data"),
    SIGNED_DYNAMIC_APPLICATION_DATA("9f4b", TagValueTypeEnum.BINARY, "Signed Dynamic Application Data", "Digital signature on critical application parameters for DDA or CDA"),
    ICC_DYNAMIC_NUMBER("9f4c", TagValueTypeEnum.BINARY, "ICC Dynamic Number", "Time-variant number generated by the ICC, to be captured by the terminal"),
    LOG_ENTRY("9f4d", TagValueTypeEnum.BINARY, "Log Entry", "Provides the SFI of the Transaction Log file and its number of records"),
    MERCHANT_NAME_AND_LOCATION("9f4e", TagValueTypeEnum.TEXT, "Merchant Name and Location", "Indicates the name and location of the merchant"),
    LOG_FORMAT("9f4f", TagValueTypeEnum.DOL, "Log Format", "List (in tag and length format) of data objects representing the logged data elements that are passed to the terminal when a transaction log record is read"),
    FCI_ISSUER_DISCRETIONARY_DATA("bf0c", TagValueTypeEnum.BINARY, "File Control Information (FCI) Issuer Discretionary Data", "Issuer discretionary part of the FCI (e.g. O/S Manufacturer proprietary data)"),
    VISA_LOG_ENTRY("df60", TagValueTypeEnum.BINARY, "VISA Log Entry", ""),
    TRACK1_DATA("56", TagValueTypeEnum.BINARY, "Track 1 Data", "Track 1 Data contains the data objects of the track 1 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
    TERMINAL_TRANSACTION_QUALIFIERS("9f66", TagValueTypeEnum.BINARY, "Terminal Transaction Qualifiers", "Provided by the reader in the GPO command and used by the card to determine processing choices based on reader functionality"),
    TRACK2_DATA("9f6b", TagValueTypeEnum.BINARY, "Track 2 Data", "Track 2 Data contains the data objects of the track 2 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
    VLP_ISSUER_AUTHORISATION_CODE("9f6e", TagValueTypeEnum.BINARY, "Visa Low-Value Payment (VLP) Issuer Authorisation Code", ""),
    EXTENDED_SELECTION("9f29", TagValueTypeEnum.BINARY, "Indicates the card's preference for the kernel on which the contactless application can be processed", ""),
    KERNEL_IDENTIFIER("9f2a", TagValueTypeEnum.BINARY, "The value to be appended to the ADF Name in the data field of the SELECT command, if the Extended Selection Support flag is present and set to 1", ""),
    MASTERCARD_UPPER_OFFLINE_AMOUNT("9f52", TagValueTypeEnum.BINARY, "Upper Cumulative Domestic Offline Transaction Amount", "Issuer specified data element indicating the required maximum cumulative offline amount allowed for the application before the transaction goes online."),
    TAG_9F56("9f56", TagValueTypeEnum.BINARY, "?", ""),
    MAG_STRIPE_APP_VERSION_NUMBER_CARD("9f6c", TagValueTypeEnum.BINARY, "Mag Stripe Application Version Number (Card)", "Must be personalized with the value 0x0001"),
    TAG_DF3E("df3e", TagValueTypeEnum.BINARY, "?", ""),
    OFFLINE_ACCUMULATOR_BALANCE("9f50", TagValueTypeEnum.BINARY, "Offline Accumulator Balance", "Represents the amount of offline spending available in the Card."),
    DRDOL("9f51", TagValueTypeEnum.BINARY, "DRDOL", "A data object in the Card that provides the Kernel with a list of data objects that must be passed to the Card in the data field of the RECOVER AC command"),
    TRANSACTION_CATEGORY_CODE("9f53", TagValueTypeEnum.BINARY, "Transaction Category Code", ""),
    DS_ODS_CARD("9f54", TagValueTypeEnum.BINARY, "DS ODS Card", ""),
    MOBILE_SUPPORT_INDICATOR("9f55", TagValueTypeEnum.BINARY, "Mobile Support Indicator", ""),
    MERCHANT_TYPE_INDICATOR("9f58", TagValueTypeEnum.BINARY, "Merchant Type Indicator (Interac)", ""),
    TERMINAL_TRANSACTION_INFORMATION("9f59", TagValueTypeEnum.BINARY, "Terminal Transaction Information (Interac)", ""),
    TERMINAL_TRANSACTION_TYPE("9f5A", TagValueTypeEnum.BINARY, "Terminal transaction Type (Interac)", ""),
    DSDOL("9f5b", TagValueTypeEnum.BINARY, "DSDOL", ""),
    DS_REQUESTED_OPERATOR_ID("9f5c", TagValueTypeEnum.BINARY, "DS Requested Operator ID", ""),
    APPLICATION_CAPABILITIES_INFORMATION("9f5d", TagValueTypeEnum.BINARY, "Application Capabilities Information", "Lists a number of card features beyond regular payment"),
    DS_ID("9f5e", TagValueTypeEnum.BINARY, "Data Storage Identifier", "Constructed as follows: Application PAN (without any 'F' padding) || Application PAN Sequence Number (+ zero padding)"),
    DS_SLOT_AVAILABILITY("9f5f", TagValueTypeEnum.BINARY, "DS Slot Availability", ""),
    CVC3_TRACK1("9f60", TagValueTypeEnum.BINARY, "CVC3 (Track1)", "The CVC3 (Track1) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
    CVC3_TRACK2("9f61", TagValueTypeEnum.BINARY, "CVC3 (Track2)", "The CVC3 (Track2) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
    PCVC3_TRACK1("9f62", TagValueTypeEnum.BINARY, "Track 1 bit map for CVC3", "PCVC3(Track1) indicates to the Kernel the positions in the discretionary data field of the Track 1 Data where the CVC3 (Track1) digits must be copied"),
    PUNTAC_TRACK1("9f63", TagValueTypeEnum.BINARY, "Track 1 bit map for UN and ATC", "PUNATC(Track1) indicates to the Kernel the positions in the discretionary data field of Track 1 Data where the Unpredictable Number (Numeric) digits and Application Transaction Counter digits have to be copied."),
    NATC_TRACK1("9f64", TagValueTypeEnum.BINARY, "Track 1 number of ATC digits", "The value of NATC(Track1) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of Track 1 Data"),
    PCVC_TRACK2("9f65", TagValueTypeEnum.BINARY, "Track 2 bit map for CVC3", "PCVC3(Track2) indicates to the Kernel the positions in the discretionary data field of the Track 2 Data where the CVC3 (Track2) digits must be copied"),
    NATC_TRACK2("9f67", TagValueTypeEnum.BINARY, "Track 2 number of ATC digits", "The value of NATC(Track2) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of the Track 2 Data"),
    UDOL("9f69", TagValueTypeEnum.BINARY, "UDOL", ""),
    UNPREDICTABLE_NUMBER_NUMERIC("9f6a", TagValueTypeEnum.BINARY, "Unpredictable Number (Numeric)", ""),
    MAG_STRIPE_APP_VERSION_NUMBER_READER("9f6d", TagValueTypeEnum.BINARY, "Mag-stripe Application Version Number (Reader)", ""),
    DS_SLOT_MANAGEMENT_CONTROL("9f6f", TagValueTypeEnum.BINARY, "DS Slot Management Control", ""),
    PROTECTED_DATA_ENVELOPE_1("9f70", TagValueTypeEnum.BINARY, "Protected Data Envelope 1", ""),
    PROTECTED_DATA_ENVELOPE_2("9f71", TagValueTypeEnum.BINARY, "Protected Data Envelope 2", ""),
    PROTECTED_DATA_ENVELOPE_3("9f72", TagValueTypeEnum.BINARY, "Protected Data Envelope 3", ""),
    PROTECTED_DATA_ENVELOPE_4("9f73", TagValueTypeEnum.BINARY, "Protected Data Envelope 4", ""),
    PROTECTED_DATA_ENVELOPE_5("9f74", TagValueTypeEnum.BINARY, "Protected Data Envelope 5", ""),
    UNPROTECTED_DATA_ENVELOPE_1("9f75", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 1", ""),
    UNPROTECTED_DATA_ENVELOPE_2("9f76", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 2", ""),
    UNPROTECTED_DATA_ENVELOPE_3("9f77", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 3", ""),
    UNPROTECTED_DATA_ENVELOPE_4("9f78", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 4", ""),
    UNPROTECTED_DATA_ENVELOPE_5("9f79", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 5", ""),
    MERCHANT_CUSTOM_DATA("9f7c", TagValueTypeEnum.BINARY, "Merchant Custom Data", ""),
    DS_SUMMARY_1("9f7d", TagValueTypeEnum.BINARY, "DS Summary 1", ""),
    DS_UNPREDICTABLE_NUMBER("9f7f", TagValueTypeEnum.BINARY, "DS Unpredictable Number", ""),
    POS_CARDHOLDER_INTERACTION_INFORMATION("df4b", TagValueTypeEnum.BINARY, "POS Cardholder Interaction Information", ""),
    DS_DIGEST_H("df61", TagValueTypeEnum.BINARY, "DS Digest H", ""),
    DS_ODS_INFO("df62", TagValueTypeEnum.BINARY, "DS ODS Info", ""),
    DS_ODS_TERM("df63", TagValueTypeEnum.BINARY, "DS ODS Term", ""),
    BALANCE_READ_BEFORE_GEN_AC("df8104", TagValueTypeEnum.BINARY, "Balance Read Before Gen AC", ""),
    BALANCE_READ_AFTER_GEN_AC("df8105", TagValueTypeEnum.BINARY, "Balance Read After Gen AC", ""),
    DATA_NEEDED("df8106", TagValueTypeEnum.BINARY, "Data Needed", ""),
    CDOL1_RELATED_DATA("df8107", TagValueTypeEnum.BINARY, "CDOL1 Related Data", ""),
    DS_AC_TYPE("df8108", TagValueTypeEnum.BINARY, "DS AC Type", ""),
    DS_INPUT_TERM("df8109", TagValueTypeEnum.BINARY, "DS Input (Term)", ""),
    DS_ODS_INFO_FOR_READER("df810a", TagValueTypeEnum.BINARY, "DS ODS Info For Reader", ""),
    DS_SUMMARY_STATUS("df810b", TagValueTypeEnum.BINARY, "DS Summary Status", ""),
    KERNEL_ID("df810c", TagValueTypeEnum.BINARY, "Kernel ID", ""),
    DSVN_TERM("df810d", TagValueTypeEnum.BINARY, "DSVN Term", ""),
    POST_GEN_AC_PUT_DATA_STATUS("df810e", TagValueTypeEnum.BINARY, "Post-Gen AC Put Data Status", ""),
    PRE_GEN_AC_PUT_DATA_STATUS("df810f", TagValueTypeEnum.BINARY, "Pre-Gen AC Put Data Status", ""),
    PROCEED_TO_WRITE_FIRST_FLAG("df8110", TagValueTypeEnum.BINARY, "Proceed To First Write Flag", ""),
    PDOL_RELATED_DATA("df8111", TagValueTypeEnum.BINARY, "PDOL Related Data", ""),
    TAGS_TO_READ("df8112", TagValueTypeEnum.BINARY, "Tags To Read", ""),
    DRDOL_RELATED_DATA("df8113", TagValueTypeEnum.BINARY, "DRDOL Related Data", ""),
    REFERENCE_CONTROL_PARAMETER("df8114", TagValueTypeEnum.BINARY, "Reference Control Parameter", ""),
    ERROR_INDICATION("df8115", TagValueTypeEnum.BINARY, "Error Indication", ""),
    USER_INTERFACE_REQUEST_DATA("df8116", TagValueTypeEnum.BINARY, "User Interface Request Data", ""),
    CARD_DATA_INPUT_CAPABILITY("df8117", TagValueTypeEnum.BINARY, "Card Data Input Capability", ""),
    CMV_CAPABILITY_CMV_REQUIRED("df8118", TagValueTypeEnum.BINARY, "CVM Capability - CVM Required", ""),
    CMV_CAPABILITY_NO_CMV_REQUIRED("df8119", TagValueTypeEnum.BINARY, "CVM Capability - No CVM Required", ""),
    DEFAULT_UDOL("df811a", TagValueTypeEnum.BINARY, "Default UDOL", ""),
    KERNEL_CONFIGURATION("df811b", TagValueTypeEnum.BINARY, "Kernel Configuration", ""),
    MAX_LIFETIME_TORN_TRANSACTION_LOG_REC("df811c", TagValueTypeEnum.BINARY, "Max Lifetime of Torn Transaction Log Record", ""),
    MAX_NUMBER_TORN_TRANSACTION_LOG_REC("df811d", TagValueTypeEnum.BINARY, "Max Number of Torn Transaction Log Records", ""),
    MAG_STRIPE_CMV_CAPABILITY_CMV_REQUIRED("df811e", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – CVM Required", ""),
    SECURITY_CAPABILITY("df811f", TagValueTypeEnum.BINARY, "Security Capability", ""),
    TERMINAL_ACTION_CODE_DEFAULT("df8120", TagValueTypeEnum.BINARY, "Terminal Action Code – Default", ""),
    TERMINAL_ACTION_CODE_DENIAL("df8121", TagValueTypeEnum.BINARY, "Terminal Action Code – Denial", ""),
    TERMINAL_ACTION_CODE_ONLINE("df8122", TagValueTypeEnum.BINARY, "Terminal Action Code – Online", ""),
    READER_CONTACTLESS_FLOOR_LIMIT("df8123", TagValueTypeEnum.BINARY, "Reader Contactless Floor Limit", ""),
    READER_CL_TRANSACTION_LIMIT_NO_CMV("df8124", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (No On-device CVM)", ""),
    READER_CL_TRANSACTION_LIMIT_CVM("df8125", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (On-device CVM)", ""),
    READER_CMV_REQUIRED_LIMIT("df8126", TagValueTypeEnum.BINARY, "Reader CVM Required Limit", ""),
    TIME_OUT_VALUE("df8127", TagValueTypeEnum.BINARY, "TIME_OUT_VALUE", ""),
    IDS_STATUS("df8128", TagValueTypeEnum.BINARY, "IDS Status", ""),
    OUTCOME_PARAMETER_SET("df8129", TagValueTypeEnum.BINARY, "Outcome Parameter Set", ""),
    DD_CARD_TRACK1("df812a", TagValueTypeEnum.BINARY, "DD Card (Track1)", ""),
    DD_CARD_TRACK2("df812b", TagValueTypeEnum.BINARY, "DD Card (Track2)", ""),
    MAG_STRIPE_CMV_CAPABILITY_NO_CMV_REQ("df812c", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – No CVM Required", ""),
    MESSAGE_HOLD_TIME("df812d", TagValueTypeEnum.BINARY, "Message Hold Time", ""),
    TORN_RECORD("ff8101", TagValueTypeEnum.BINARY, "Torn Record", ""),
    TAGS_TO_WRITE_BEFORE_GEN_AC("ff8102", TagValueTypeEnum.BINARY, "Tags To Write Before Gen AC", ""),
    TAGS_TO_WRITE_AFTER_GEN_AC("ff8103", TagValueTypeEnum.BINARY, "Tags To Write After Gen AC", ""),
    DATA_TO_SEND("ff8104", TagValueTypeEnum.BINARY, "Data To Send", ""),
    DATA_RECORD("ff8105", TagValueTypeEnum.BINARY, "Data Record", ""),
    DISCRETIONARY_DATA("ff8106", TagValueTypeEnum.BINARY, "Discretionary Data", "") ;

    private val idBytes: ByteArray = BytesUtils.fromString(idHex)

    private val type: TagTypeEnum = if (BytesUtils.matchBitByBitIndex(idBytes[0].toInt() and 0xFF, 5)) {
        TagTypeEnum.CONSTRUCTED
    } else {
        TagTypeEnum.PRIMITIVE
    }

    private val tagClass: ITag.Class = when ((idBytes[0].toInt() ushr 6) and 0x03) {
        0x01 -> ITag.Class.APPLICATION
        0x02 -> ITag.Class.CONTEXT_SPECIFIC
        0x03 -> ITag.Class.PRIVATE
        else -> ITag.Class.UNIVERSAL
    }

    override fun getTagBytes(): ByteArray = idBytes

    override fun getName(): String = tagName

    override fun getDescription(): String = tagDescription

    override fun getTagValueType(): TagValueTypeEnum = tagValueType

    override fun getType(): TagTypeEnum = type

    override fun getTagClass(): ITag.Class = tagClass

    override fun isConstructed(): Boolean = type == TagTypeEnum.CONSTRUCTED

    override fun getNumTagBytes(): Int = idBytes.size

    override fun toString(): String {
        return buildString {
            append("Tag[")
            append(BytesUtils.bytesToString(idBytes))
            append("] Name=")
            append(tagName)
            append(", TagType=")
            append(type)
            append(", ValueType=")
            append(tagValueType)
            append(", Class=")
            append(tagClass)
        }
    }

    companion object {
        private val allTags = entries
        private val tags = allTags.associateBy {
            ByteArrayWrapper.wrapperAround(it.getTagBytes())
        }

        fun find(tagBytes: ByteArray): ITag? {
            return tags[ByteArrayWrapper.wrapperAround(tagBytes)]
        }

        fun getNotNull(tagBytes: ByteArray): ITag {
            return find(tagBytes) ?: createUnknownTag(tagBytes)
        }

        private fun createUnknownTag(tagBytes: ByteArray): ITag {
            return CustomTag(tagBytes, TagValueTypeEnum.BINARY, "[UNKNOWN TAG]", "")
        }
    }
}
