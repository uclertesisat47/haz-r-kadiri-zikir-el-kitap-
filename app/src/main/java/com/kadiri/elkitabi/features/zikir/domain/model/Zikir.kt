package com.kadiri.elkitabi.features.zikir.domain.model

data class Zikir(
    val id: Int,
    val arabicName: String,
    val turkishName: String,
    val arabicText: String,
    val turkishMeaning: String,
    val hedefSayilar: List<Int>,
    val varsayilanHedef: Int,
    val fazileti: String,
    val kategori: ZikirKategori,
    val sesResId: Int? = null
)

data class ZikirSession(
    val id: Long = 0,
    val zikirId: Int,
    val zikirAdi: String,
    val count: Int,
    val hedef: Int,
    val tarih: Long,
    val sure: Long,
    val tamamlandi: Boolean,
    val notlar: String = ""
)

data class ZikirIstatistik(
    val toplamSayim: Long,
    val bugunSayim: Int,
    val haftaSayim: Int,
    val aySayim: Int,
    val enCokZikir: String,
    val streak: Int,
    val toplamSure: Long,
    val haftalikData: List<GunlukZikir>,
    val aylikData: List<GunlukZikir>
)

data class GunlukZikir(
    val gun: String,
    val sayi: Int
)

data class ZikirHedef(
    val zikirId: Int,
    val gunlukHedef: Int,
    val haftalikHedef: Int
)

data class OzelZikir(
    val id: Long = 0,
    val isim: String,
    val arabicText: String,
    val hedef: Int,
    val renk: String
)

enum class ZikirKategori {
    KADİRİ_OZEL,
    GENEL,
    ESMA_UL_HUSNA,
    OZEL
}

enum class ZikirSesi {
    SESSIZ,
    TITREŞIM,
    TEBİH_SESİ,
    NEY_SESİ,
    SU_SESİ
}
