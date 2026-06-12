package com.kadiri.elkitabi.features.kitap.domain.model

data class Kitap(
    val id: String,
    val baslik: String,
    val arabicBaslik: String = "",
    val aciklama: String = "",
    val toplamBolum: Int = 0,
    val okunmusBolum: Int = 0,
    val kapakResim: String = ""
) {
    val ilerlemeYuzdesi: Float
        get() = if (toplamBolum == 0) 0f else okunmusBolum.toFloat() / toplamBolum
}

data class KitapBolum(
    val id: Long = 0,
    val kitapId: String,
    val bolumNo: Int,
    val baslik: String,
    val arabicBaslik: String = "",
    val icerik: String,
    val sayfa: Int = 0,
    val okundu: Boolean = false
)

data class KitapSayfa(
    val id: Long = 0,
    val bolumId: Long,
    val sayfaNo: Int,
    val arabicIcerik: String = "",
    val turkceMeali: String = "",
    val aciklama: String = ""
)

data class YerImi(
    val id: Long = 0,
    val kitapId: String,
    val bolumId: Long,
    val sayfaNo: Int,
    val not: String = "",
    val tarih: Long = System.currentTimeMillis()
)

val KADIRI_KITAPLARI = listOf(
    Kitap(
        id       = "el_kitabi",
        baslik   = "Kadiri Tarikatı El Kitabı",
        arabicBaslik = "كتاب الطريقة القادرية",
        aciklama = "Kadiri tarikatının temel esasları, âdâb ve erkânı"
    ),
    Kitap(
        id       = "hizip_bahri",
        baslik   = "Hizb-i Bahri",
        arabicBaslik = "حزب البحر",
        aciklama = "Hz. Şazelî'nin meşhur duası"
    ),
    Kitap(
        id       = "kaside_burde",
        baslik   = "Kasîde-i Bürde",
        arabicBaslik = "قصيدة البردة",
        aciklama = "Hz. Peygamber'e medh ü sena"
    ),
    Kitap(
        id       = "delailul_hayrat",
        baslik   = "Delâilü'l-Hayrât",
        arabicBaslik = "دلائل الخيرات",
        aciklama = "Hz. Peygamber'e salât ü selam kitabı"
    )
)
