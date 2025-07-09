# Nfc-Emv-Card-Reader

A Kotlin-based EMV smart card reader for Android using NFC and the APDU protocol.  
This project enables reading public data from contactless EMV cards such as PAN, expiry date, and track data by parsing TLV responses in compliance with ISO 7816.

<div align="center">

| Idle State | Card Read State |
|---------------|-----------------|
<img src="https://github.com/user-attachments/assets/19fd055c-90c5-4cb9-be09-b9d4c5a42271" width="300"/> | <img src="https://github.com/user-attachments/assets/9289c519-2f4d-4934-a5fd-52e6659ad831" width="300"/>

</div>

---

## 📦 Features

- 📲 NFC-based smart card communication via APDU protocol  
- 🧾 TLV parsing and EMV Tag extraction  
- 💳 Reads PAN, expiry date, and Track 1 & 2 data  
- 🧠 Clean, Kotlin-first implementation  
- 📱 Fully compatible with Android NFC APIs  

---

## ⚙️ Tech Stack

- **Kotlin**
- **Android NFC API**
- **APDU Protocol (ISO 7816-4)**
- **EMV Tag Parsing**
- **Jetpack Components**

---

## 🛠️ Setup

1. Clone the project:
```bash
git clone https://github.com/BerkayyKurtoglu/Nfc-Emv-Card-Reader.git
