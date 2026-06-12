package com.kadiri.elkitabi.features.kitap.data.repository

import com.kadiri.elkitabi.features.kitap.data.local.KitapDao
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapBolumEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.YerImiEntity
import com.kadiri.elkitabi.features.kitap.domain.model.KitapBolum
import com.kadiri.elkitabi.features.kitap.domain.model.YerImi
import com.kadiri.elkitabi.features.kitap.domain.repository.KitapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KitapRepositoryImpl @Inject constructor(
    private val kitapDao: KitapDao
) : KitapRepository {

    override fun getBolumler(kitapId: String): Flow<List<KitapBolum>> =
        kitapDao.getBolumler(kitapId).map { list -> list.map { it.toDomain() } }

    override suspend fun getBolum(bolumId: Long): KitapBolum? =
        kitapDao.getBolum(bolumId)?.toDomain()

    override suspend fun seedBolumlerIfEmpty(kitapId: String) {
        val count = kitapDao.getToplamBolumSayisi(kitapId)
        if (count == 0) {
            val bolumler = buildSeedBolumler(kitapId)
            kitapDao.insertBolumler(bolumler)
        }
    }

    override suspend fun markBolumAsRead(bolumId: Long) =
        kitapDao.markAsRead(bolumId)

    override suspend fun getKitapIlerlemesi(kitapId: String): Pair<Int, Int> =
        Pair(
            kitapDao.getOkunanBolumSayisi(kitapId),
            kitapDao.getToplamBolumSayisi(kitapId)
        )

    override fun getYerImleri(kitapId: String): Flow<List<YerImi>> =
        kitapDao.getYerImleri(kitapId).map { list -> list.map { it.toDomain() } }

    override suspend fun addYerImi(yerImi: YerImi): Long =
        kitapDao.insertYerImi(yerImi.toEntity())

    override suspend fun deleteYerImi(yerImiId: Long) =
        kitapDao.deleteYerImi(yerImiId)

    override suspend fun isYerImiExist(kitapId: String, sayfaNo: Int): Boolean =
        kitapDao.isYerImiExist(kitapId, sayfaNo)

    // ─── Mapper'lar ─────────────────────────────────────────────────────────

    private fun KitapBolumEntity.toDomain() = KitapBolum(
        id           = id,
        kitapId      = kitapId,
        bolumNo      = bolumNo,
        baslik       = baslik,
        arabicBaslik = arabicBaslik,
        icerik       = icerik,
        sayfa        = sayfa,
        okundu       = okundu
    )

    private fun YerImiEntity.toDomain() = YerImi(
        id      = id,
        kitapId = kitapId,
        bolumId = bolumId,
        sayfaNo = sayfaNo,
        not     = not,
        tarih   = tarih
    )

    private fun YerImi.toEntity() = YerImiEntity(
        id      = id,
        kitapId = kitapId,
        bolumId = bolumId,
        sayfaNo = sayfaNo,
        not     = not,
        tarih   = tarih
    )

    // ─── Seed Data ───────────────────────────────────────────────────────────

    private fun buildSeedBolumler(kitapId: String): List<KitapBolumEntity> = when (kitapId) {
        "el_kitabi" -> listOf(
            KitapBolumEntity(kitapId = kitapId, bolumNo = 1,  baslik = "Giriş ve Niyyet",               arabicBaslik = "المقدمة والنية",       icerik = "Kadiri tarikatına intisabın âdâbı ve niyet esasları. Tasavvuf yoluna girerken kalp ile dil uyumunun önemi. Seyri sülûkun başlangıcında lazım gelen edep ve tevazu..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 2,  baslik = "Silsile-i Şerife",              arabicBaslik = "السلسلة الشريفة",       icerik = "Hz. Abdülkadir Geylânî Hazretleri'ne uzanan mübarek silsile. Her halkada emanet edilen nur ve feyz..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 3,  baslik = "Zikir ve Evrâd",                arabicBaslik = "الذكر والأوراد",        icerik = "Sabah ve akşam vird esasları. Tesbih, tahmid, tekbir ve tehlil adedi. La ilahe illallah zikriyle kalbin tasfiyesi..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 4,  baslik = "Hatm-i Hâcegân",                arabicBaslik = "ختم الخواجكان",         icerik = "Hâcegân silsilesinde icra edilen hatm-i şerif. Fâtiha, İhlâs ve salavat-ı şerife adedi..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 5,  baslik = "Sohbet Âdâbı",                  arabicBaslik = "آداب الصحبة",           icerik = "Mürşid ile sohbetin şartları. Derviş kardeşliği ve hürmet. Sohbet meclisinde edep kaideleri..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 6,  baslik = "Tevbe ve İstihfar",              arabicBaslik = "التوبة والاستغفار",     icerik = "Gerçek tevbenin şartları. Günah ve hata karşısında dervişin tutumu. Seyyidü'l-istiğfar duası..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 7,  baslik = "Namaz ve İbadet",                arabicBaslik = "الصلاة والعبادة",       icerik = "Farz namazların huşu ile edası. Sünnet ve nafile namazlar. İsrâf-ı vakitten kaçınma..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 8,  baslik = "Halvet ve Uzlet",                arabicBaslik = "الخلوة والعزلة",        icerik = "Kalp tasfiyesi için halvetin önemi. Şartları ve süresi. Halvete girişte okunacak zikirler..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 9,  baslik = "Muhabbetullah",                  arabicBaslik = "محبة الله",              icerik = "Allah sevgisinin kalpteki tezahürü. Mahabbet makamına ulaşmanın yolları. Aşk-ı ilâhî..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 10, baslik = "Hizmet ve Tevazu",               arabicBaslik = "الخدمة والتواضع",       icerik = "Dervişin hizmet anlayışı. Kibir ve ucb'dan kaçınma. Eline-beline-diline sahip olma..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 11, baslik = "Rüya ve Keşif",                  arabicBaslik = "الرؤيا والكشف",         icerik = "Sâlih rüyaların yorumlanması. Keşf ve ilhamın kaynağı. Mükaşefenin şartları..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 12, baslik = "Son Nasihatler",                 arabicBaslik = "الوصايا الأخيرة",       icerik = "Yola devam etmek için şart olan hususlar. Ölüm karşısında mümin kalp. Hüsnü hâtime duası...")
        )
        "kaside_burde" -> listOf(
            KitapBolumEntity(kitapId = kitapId, bolumNo = 1, baslik = "Nesîb (Aşk Şikâyeti)",          arabicBaslik = "النسيب",                 icerik = "أَمِن تَذَكُّرِ جِيرانٍ بِذِي سَلَمِ — مَزَجْتَ دَمْعاً جَرى مِن مُقلَةٍ بِدَمِ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 2, baslik = "Nefsi Kınama",                   arabicBaslik = "في نهي النفس",           icerik = "أَمَّا هَواكَ فَلَم أُعْمِل بِهِ حَيَلاً — وَلا أَجَلْتُ لَهُ فِكراً وَلا فَعَلا..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 3, baslik = "Peygamber'e Methiye",             arabicBaslik = "في مدح النبي",           icerik = "مُحَمَّدٌ سَيِّدُ الكَونَينِ وَالثَّقَلَين — وَالفَرِيقَين مِن عُربٍ وَمِن عَجَم..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 4, baslik = "Mevlid ve Mu'cizât",             arabicBaslik = "في المولد والمعجزات",    icerik = "أَبانَ مَولِدُهُ عَن طِيبِ عُنصُرِهِ — يا طِيبَ مُبتَدَأٍ مِنهُ وَمُختَتَمِ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 5, baslik = "Kur'ân Medhi",                   arabicBaslik = "في القرآن",              icerik = "آياتُ حَقٍّ مِنَ الرَّحمنِ مُحدَثَةٌ — قَديمَةٌ صِفَةُ المَوصوفِ بِالقِدَمِ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 6, baslik = "İsrâ ve Mi'râc",                 arabicBaslik = "في المعراج",             icerik = "يا خَيرَ مَن يَمَّمَ العافونَ ساحَتَهُ — سَعياً وَمَن هُوَ مَأمولٌ وَمُحتَرَمُ...")
        )
        "delailul_hayrat" -> listOf(
            KitapBolumEntity(kitapId = kitapId, bolumNo = 1, baslik = "Mukaddime ve Dua",               arabicBaslik = "المقدمة والدعاء",        icerik = "بِسْمِ اللهِ الرَّحْمنِ الرَّحِيمِ — اللهم صَلِّ وَسَلِّمْ وَبارِكْ عَلى سَيِّدِنا مُحَمَّدٍ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 2, baslik = "Hizb-i Evvel",                   arabicBaslik = "الحزب الأول",            icerik = "اللهم صَلِّ عَلى مُحَمَّدٍ عَبدِكَ وَنَبِيِّكَ وَرَسولِكَ النَّبِيِّ الأُمِّيِّ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 3, baslik = "Esmâ-i Nebeviyye",               arabicBaslik = "الأسماء النبوية",        icerik = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ — أَحْمَدَ، حَامِدٍ، مَحْمُودٍ، حَمَّادٍ..."),
            KitapBolumEntity(kitapId = kitapId, bolumNo = 4, baslik = "Salât ü Selam",                  arabicBaslik = "الصلاة والسلام",         icerik = "اللَّهُمَّ صَلِّ وَسَلِّمْ — صَلاةً وَسَلَامًا كَامِلَيْنِ عَلَى نُورِ الأَنْوَارِ...")
        )
        else -> listOf(
            KitapBolumEntity(kitapId = kitapId, bolumNo = 1, baslik = "Birinci Bölüm", arabicBaslik = "", icerik = "İçerik yüklenecek...")
        )
    }
}
