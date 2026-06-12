package com.kadiri.elkitabi.features.zikir.data.repository

import android.content.Context
import com.kadiri.elkitabi.features.zikir.data.local.ZikirDao
import com.kadiri.elkitabi.features.zikir.data.local.ZikirSessionDao
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirHedefEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirSessionEntity
import com.kadiri.elkitabi.features.zikir.domain.model.GunlukZikir
import com.kadiri.elkitabi.features.zikir.domain.model.OzelZikir
import com.kadiri.elkitabi.features.zikir.domain.model.Zikir
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirHedef
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirIstatistik
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirKategori
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSession
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import com.kadiri.elkitabi.core.utils.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZikirRepositoryImpl @Inject constructor(
    private val zikirDao: ZikirDao,
    private val sessionDao: ZikirSessionDao,
    @ApplicationContext private val context: Context
) : ZikirRepository {

    override fun getAllZikirler(): Flow<List<Zikir>> =
        zikirDao.getAllZikirler().map { list -> list.map { it.toDomain() } }

    override suspend fun getZikirById(id: Int): Zikir? =
        zikirDao.getZikirById(id)?.toDomain()

    override suspend fun saveSession(session: ZikirSession): Long {
        return sessionDao.insertSession(session.toEntity())
    }

    override fun getAllSessions(): Flow<List<ZikirSession>> =
        sessionDao.getAllSessions().map { list -> list.map { it.toDomain() } }

    override suspend fun getIstatistik(): ZikirIstatistik {
        val total       = sessionDao.getTotalCount() ?: 0L
        val todayStart  = DateUtils.startOfDay(System.currentTimeMillis())
        val weekStart   = DateUtils.startOfWeek()
        val monthStart  = DateUtils.startOfMonth()
        val bugun       = sessionDao.getCountFrom(todayStart) ?: 0
        val hafta       = sessionDao.getCountFrom(weekStart) ?: 0
        val ay          = sessionDao.getCountFrom(monthStart) ?: 0
        val enCok       = sessionDao.getMostUsedZikir()?.zikirAdi ?: ""
        val totalSure   = sessionDao.getTotalDuration() ?: 0L
        val activeDays  = sessionDao.getActiveDays()
        val streak      = calculateStreak(activeDays)

        val haftalikData = buildWeeklyData(weekStart)
        val aylikData    = buildMonthlyData(monthStart)

        return ZikirIstatistik(
            toplamSayim  = total,
            bugunSayim   = bugun,
            haftaSayim   = hafta,
            aySayim      = ay,
            enCokZikir   = enCok,
            streak       = streak,
            toplamSure   = totalSure,
            haftalikData = haftalikData,
            aylikData    = aylikData
        )
    }

    override suspend fun getHedef(zikirId: Int): ZikirHedef? =
        zikirDao.getHedef(zikirId)?.let { ZikirHedef(it.zikirId, it.gunlukHedef, it.haftalikHedef) }

    override suspend fun setHedef(hedef: ZikirHedef) {
        zikirDao.insertHedef(ZikirHedefEntity(hedef.zikirId, hedef.gunlukHedef, hedef.haftalikHedef))
    }

    override suspend fun ekleOzelZikir(ozel: OzelZikir): Long {
        val entity = ZikirEntity(
            id              = (10000..99999).random(),
            arabicName      = ozel.arabicText.take(30),
            turkishName     = ozel.isim,
            arabicText      = ozel.arabicText,
            turkishMeaning  = "",
            hedefSayilar    = ozel.hedef.toString(),
            varsayilanHedef = ozel.hedef,
            fazileti        = "",
            kategori        = ZikirKategori.OZEL.name
        )
        zikirDao.insertZikir(entity)
        return entity.id.toLong()
    }

    override suspend fun seedZikirlerIfEmpty() {
        if (zikirDao.getZikirCount() > 0) return
        val seed = buildSeedZikirler()
        zikirDao.insertZikirler(seed)
    }

    private fun calculateStreak(activeDays: List<String>): Int {
        if (activeDays.isEmpty()) return 0
        var streak = 0
        val cal = Calendar.getInstance()
        var expected = DateUtils.formatDate(cal.time, "yyyy-MM-dd")
        for (day in activeDays) {
            if (day == expected) {
                streak++
                cal.add(Calendar.DAY_OF_YEAR, -1)
                expected = DateUtils.formatDate(cal.time, "yyyy-MM-dd")
            } else break
        }
        return streak
    }

    private suspend fun buildWeeklyData(weekStart: Long): List<GunlukZikir> {
        val days = listOf("Pzt","Sal","Çar","Per","Cum","Cmt","Paz")
        return days.mapIndexed { i, gun ->
            val start = weekStart + i * 86_400_000L
            val end   = start + 86_400_000L
            val sayi  = sessionDao.getSessionsBetween(start, end).sumOf { it.count }
            GunlukZikir(gun, sayi)
        }
    }

    private suspend fun buildMonthlyData(monthStart: Long): List<GunlukZikir> {
        val result = mutableListOf<GunlukZikir>()
        val cal    = Calendar.getInstance()
        cal.timeInMillis = monthStart
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (d in 1..daysInMonth) {
            val start = monthStart + (d - 1) * 86_400_000L
            val end   = start + 86_400_000L
            val sayi  = sessionDao.getSessionsBetween(start, end).sumOf { it.count }
            result.add(GunlukZikir(d.toString(), sayi))
        }
        return result
    }

    private fun buildSeedZikirler(): List<ZikirEntity> = listOf(
        ZikirEntity(1,  "سُبْحَانَ اللّٰهِ", "Sübhânallah",
            "سُبْحَانَ اللّٰهِ", "Allah her türlü noksanlıktan münezzehtir",
            "33,99,100,500,1000", 33, "Her tesbih için büyük sevap vardır.", "GENEL"),
        ZikirEntity(2,  "اَلْحَمْدُ لِلّٰهِ", "Elhamdülillah",
            "اَلْحَمْدُ لِلّٰهِ", "Hamd yalnızca Allah'a mahsustur",
            "33,99,100,500", 33, "Mizanı dolduran bir zikirdir.", "GENEL"),
        ZikirEntity(3,  "اَللّٰهُ أَكْبَرُ", "Allâhu Ekber",
            "اَللّٰهُ أَكْبَرُ", "Allah her şeyden büyüktür",
            "33,99,100,500,1000", 33, "Gökleri dolduran tesbihtir.", "GENEL"),
        ZikirEntity(4,  "لَا إِلٰهَ إِلَّا اللّٰهُ", "Lâ ilâhe illallah",
            "لَا إِلٰهَ إِلَّا اللّٰهُ", "Allah'tan başka ilah yoktur",
            "100,500,1000", 100, "Zikrin en faziletlisidir.", "GENEL"),
        ZikirEntity(5,  "سُبْحَانَ اللّٰهِ وَبِحَمْدِهِ", "Sübhânallahi ve bihamdihî",
            "سُبْحَانَ اللّٰهِ وَبِحَمْدِهِ", "Allah'ı hamd ile tesbih ederim",
            "100", 100, "Mizanda ağır gelen bir zikirdir.", "GENEL"),
        ZikirEntity(6,  "سُبْحَانَ اللّٰهِ الْعَظِيمِ", "Sübhânallâhil azîm",
            "سُبْحَانَ اللّٰهِ الْعَظِيمِ", "Yüce Allah'ı tesbih ederim",
            "100", 100, "Dile hafif, mizanda ağır bir zikirdir.", "GENEL"),
        ZikirEntity(7,  "أَسْتَغْفِرُ اللّٰهَ", "Estağfirullah",
            "أَسْتَغْفِرُ اللّٰهَ", "Allah'tan bağışlanma dilerim",
            "70,100,1000", 70, "Günahlardan temizlenmenin anahtarıdır.", "GENEL"),
        ZikirEntity(8,  "أَسْتَغْفِرُ اللّٰهَ الْعَظِيمَ وَأَتُوبُ إِلَيْهِ", "Estağfirullâhel azîm",
            "أَسْتَغْفِرُ اللّٰهَ الْعَظِيمَ وَأَتُوبُ إِلَيْهِ",
            "Yüce Allah'tan bağışlanma diler ve tevbe ederim",
            "100", 100, "Tam istiğfar duasıdır.", "GENEL"),
        ZikirEntity(9,  "اَللّٰهُمَّ صَلِّ عَلٰى سَيِّدِنَا مُحَمَّدٍ", "Salevât-ı Şerîfe",
            "اَللّٰهُمَّ صَلِّ عَلٰى سَيِّدِنَا مُحَمَّدٍ",
            "Allah'ım, efendimiz Muhammed'e salat eyle",
            "10,100,1000", 10, "Hz. Peygamber'e salevat getirmek büyük fazilettir.", "GENEL"),
        ZikirEntity(10, "حَسْبُنَا اللّٰهُ وَنِعْمَ الْوَكِيلُ", "Hasbünallâhü ve ni'mel vekîl",
            "حَسْبُنَا اللّٰهُ وَنِعْمَ الْوَكِيلُ",
            "Allah bize yeter; O ne güzel vekildir",
            "450", 450, "Hz. İbrahim (as)'ın ateşe atılırken okuduğu duadır.", "GENEL"),
        ZikirEntity(11, "يَا حَيُّ يَا قَيُّومُ", "Yâ Hayy Yâ Kayyûm",
            "يَا حَيُّ يَا قَيُّومُ", "Ey Diri olan, ey Kaim olan",
            "33,100", 33, "İsm-i Azam'dandır.", "ESMA_UL_HUSNA"),
        ZikirEntity(12, "يَا رَحْمَنُ يَا رَحِيمُ", "Yâ Rahmân Yâ Rahîm",
            "يَا رَحْمَنُ يَا رَحِيمُ", "Ey Rahman, ey Rahim",
            "100", 100, "Allah'ın rahmeti ile müjdelenen zikirdir.", "ESMA_UL_HUSNA"),
        ZikirEntity(13, "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللّٰهِ", "Lâ havle ve lâ kuvvete",
            "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللّٰهِ الْعَلِيِّ الْعَظِيمِ",
            "Güç ve kuvvet ancak yüce ve büyük Allah'a aittir",
            "100", 100, "Cennet hazinelerinden bir hazinedir.", "GENEL"),
        ZikirEntity(14, "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيمِ", "Bismillâhirrahmânirrahîm",
            "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيمِ", "Rahman ve Rahim olan Allah'ın adıyla",
            "786", 786, "Her hayırlı işe başlarken okunur.", "GENEL"),
        ZikirEntity(15, "قُلْ هُوَ اللّٰهُ أَحَدٌ", "İhlâs Sûresi",
            "قُلْ هُوَ اللّٰهُ أَحَدٌ ۞ اللّٰهُ الصَّمَدُ ۞ لَمْ يَلِدْ وَلَمْ يُولَدْ ۞ وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ",
            "Allah birdir, Samet'tir, doğmamış ve doğurmamıştır",
            "3,7,41,1001", 3, "Kur'an'ın üçte birine denktir.", "GENEL"),
        ZikirEntity(16, "اَللّٰهُ لَا إِلٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ", "Âyete'l-Kürsî",
            "اَللّٰهُ لَا إِلٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ",
            "Allah; O'ndan başka ilah yoktur. Diridir, Kayyum'dur.",
            "7,41", 7, "Kuran'ın en büyük ayetidir.", "GENEL"),
        ZikirEntity(17, "لَا إِلٰهَ إِلَّا اللّٰهُ مُحَمَّدٌ رَسُولُ اللّٰهِ", "Kadiri Kelime-i Tevhid",
            "لَا إِلٰهَ إِلَّا اللّٰهُ مُحَمَّدٌ رَسُولُ اللّٰهِ",
            "Allah'tan başka ilah yoktur, Muhammed Allah'ın Rasulüdür",
            "100,300,500", 100, "Kadiri tarikatının özel tevhid zikri.", "KADİRİ_OZEL"),
        ZikirEntity(18, "يَا قَادِرُ", "Yâ Kâdir",
            "يَا قَادِرُ", "Ey Kadir olan Allah",
            "100", 100, "Kadiri tarikatının isim zikri.", "KADİRİ_OZEL"),
        ZikirEntity(19, "يَا قَيُّومُ", "Yâ Kayyûm",
            "يَا قَيُّومُ", "Ey her şeyin kaim olduğu Allah",
            "100,300", 100, "Kadiri vird zikridir.", "KADİRİ_OZEL"),
        ZikirEntity(20, "اَللّٰهُمَّ صَلِّ عَلٰى مُحَمَّدٍ وَآلِ مُحَمَّدٍ", "Salevât-ı Kadiri",
            "اَللّٰهُمَّ صَلِّ عَلٰى مُحَمَّدٍ وَآلِ مُحَمَّدٍ وَصَحْبِهِ أَجْمَعِينَ",
            "Allah'ım Muhammed'e, ailesine ve tüm sahabesine salat et",
            "10,100,1000", 100, "Kadiri vird salevâtıdır.", "KADİRİ_OZEL")
    )

    private fun ZikirEntity.toDomain() = Zikir(
        id              = id,
        arabicName      = arabicName,
        turkishName     = turkishName,
        arabicText      = arabicText,
        turkishMeaning  = turkishMeaning,
        hedefSayilar    = hedefSayilar.split(",").mapNotNull { it.trim().toIntOrNull() },
        varsayilanHedef = varsayilanHedef,
        fazileti        = fazileti,
        kategori        = ZikirKategori.valueOf(kategori),
        sesResId        = sesResId
    )

    private fun ZikirSession.toEntity() = ZikirSessionEntity(
        id         = id,
        zikirId    = zikirId,
        zikirAdi   = zikirAdi,
        count      = count,
        hedef      = hedef,
        tarih      = tarih,
        sure       = sure,
        tamamlandi = tamamlandi,
        notlar     = notlar
    )

    private fun ZikirSessionEntity.toDomain() = ZikirSession(
        id         = id,
        zikirId    = zikirId,
        zikirAdi   = zikirAdi,
        count      = count,
        hedef      = hedef,
        tarih      = tarih,
        sure       = sure,
        tamamlandi = tamamlandi,
        notlar     = notlar
    )
}
