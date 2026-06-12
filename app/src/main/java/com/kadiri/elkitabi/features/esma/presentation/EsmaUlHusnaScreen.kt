package com.kadiri.elkitabi.features.esma.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextMedium
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

private data class Esma(val arapca: String, val turkce: String, val anlami: String)

private val ESMA_UL_HUSNA = listOf(
    Esma("اللَّهُ",         "Allah",         "Zât-ı ilâhiyyeye has özel isim"),
    Esma("الرَّحْمَنُ",    "er-Rahmân",     "Dünya ve âhirette bütün varlıklara merhamet eden"),
    Esma("الرَّحِيمُ",     "er-Rahîm",      "Ahirette yalnız mü'minlere merhamet eden"),
    Esma("الْمَلِكُ",      "el-Melik",      "Mutlak hükümranlık sahibi"),
    Esma("الْقُدُّوسُ",    "el-Kuddûs",     "Her türlü noksanlıktan münezzeh"),
    Esma("السَّلاَمُ",     "es-Selâm",      "Her türlü bela ve âfetten selâmete erdiren"),
    Esma("الْمُؤْمِنُ",    "el-Mü'min",     "Güven veren, emin kılan"),
    Esma("الْمُهَيْمِنُ",  "el-Müheymin",   "Her şeyi gözetip koruyan"),
    Esma("الْعَزِيزُ",     "el-Azîz",       "İzzet ve onur sahibi, galip gelen"),
    Esma("الْجَبَّارُ",    "el-Cebbâr",     "İradesini mutlaka yürüten"),
    Esma("الْمُتَكَبِّرُ", "el-Mütekebbir", "Büyüklük ve kibriyâ sahibi"),
    Esma("الْخَالِقُ",     "el-Hâlik",      "Her şeyi yaratan"),
    Esma("الْبَارِئُ",     "el-Bâri'",      "Her şeyi yaratan ve şekil veren"),
    Esma("الْمُصَوِّرُ",   "el-Musavvir",   "Her şeye şekil veren"),
    Esma("الْغَفَّارُ",    "el-Gaffâr",     "Günahları çok bağışlayan"),
    Esma("الْقَهَّارُ",    "el-Kahhâr",     "Her şeye galip gelen"),
    Esma("الْوَهَّابُ",    "el-Vehhâb",     "Karşılıksız veren"),
    Esma("الرَّزَّاقُ",    "er-Rezzâk",     "Rızıkları yaratan ve veren"),
    Esma("الْفَتَّاحُ",    "el-Fettâh",     "Her şeyi açan, hükmeden"),
    Esma("الْعَلِيمُ",     "el-Alîm",       "Her şeyi bilen"),
    Esma("الْقَابِضُ",     "el-Kâbız",      "Daraltan, tutan"),
    Esma("الْبَاسِطُ",     "el-Bâsıt",      "Genişleten, açan"),
    Esma("الْخَافِضُ",     "el-Hâfıd",      "Alçaltan"),
    Esma("الرَّافِعُ",     "er-Râfi'",      "Yükselten"),
    Esma("الْمُعِزُّ",     "el-Mu'izz",     "İzzet veren"),
    Esma("الْمُذِلُّ",     "el-Müzill",     "Zillete düşüren"),
    Esma("السَّمِيعُ",     "es-Semî'",      "Her şeyi işiten"),
    Esma("الْبَصِيرُ",     "el-Basîr",      "Her şeyi gören"),
    Esma("الْحَكَمُ",      "el-Hakem",      "Hükmeden, hükmü kesin"),
    Esma("الْعَدْلُ",      "el-Adl",        "Son derece adil olan"),
    Esma("اللَّطِيفُ",     "el-Latîf",      "En ince şeyleri bilen, lütufkâr"),
    Esma("الْخَبِيرُ",     "el-Habîr",      "Her şeyden haberdar"),
    Esma("الْحَلِيمُ",     "el-Halîm",      "Cezada acele etmeyen"),
    Esma("الْعَظِيمُ",     "el-Azîm",       "Yüceliği sonsuz olan"),
    Esma("الْغَفُورُ",     "el-Gafûr",      "Bağışlaması çok olan"),
    Esma("الشَّكُورُ",     "eş-Şekûr",      "Az amele çok sevap veren"),
    Esma("الْعَلِيُّ",     "el-Aliyy",      "Yüce, yüksek"),
    Esma("الْكَبِيرُ",     "el-Kebîr",      "Her şeyden büyük"),
    Esma("الْحَفِيظُ",     "el-Hafîz",      "Her şeyi koruyan"),
    Esma("الْمُقِيتُ",     "el-Mukît",      "Güce ve rızka yeten"),
    Esma("الْحَسِيبُ",     "el-Hasîb",      "Hesap gören"),
    Esma("الْجَلِيلُ",     "el-Celîl",      "Celâl sahibi"),
    Esma("الْكَرِيمُ",     "el-Kerîm",      "Keremi bol olan"),
    Esma("الرَّقِيبُ",     "er-Rakîb",      "Her şeyi gözetleyen"),
    Esma("الْمُجِيبُ",     "el-Mücîb",      "Duaları kabul eden"),
    Esma("الْوَاسِعُ",     "el-Vâsi'",      "Rahmeti geniş olan"),
    Esma("الْحَكِيمُ",     "el-Hakîm",      "Hikmet sahibi"),
    Esma("الْوَدُودُ",     "el-Vedûd",      "Kullarını seven"),
    Esma("الْمَجِيدُ",     "el-Mecîd",      "Şan ve şeref sahibi"),
    Esma("الْبَاعِثُ",     "el-Bâis",       "Ölüleri dirilten"),
    Esma("الشَّهِيدُ",     "eş-Şehîd",      "Her yerde hazır ve nâzır"),
    Esma("الْحَقُّ",       "el-Hakk",       "Varlığı değişmez"),
    Esma("الْوَكِيلُ",     "el-Vekîl",      "Güvenilen, işleri yoluna koyan"),
    Esma("الْقَوِيُّ",     "el-Kaviyy",     "Kuvvet sahibi"),
    Esma("الْمَتِينُ",     "el-Metîn",      "Sağlamlığı sonsuz"),
    Esma("الْوَلِيُّ",     "el-Veliyy",     "Dost, koruyucu"),
    Esma("الْحَمِيدُ",     "el-Hamîd",      "Her türlü övgüye layık"),
    Esma("الْمُحْصِي",     "el-Muhsî",      "Her şeyi sayıp bilen"),
    Esma("الْمُبْدِئُ",    "el-Mübdi'",     "Maddesiz yaratan"),
    Esma("الْمُعِيدُ",     "el-Muîd",       "Ölümden sonra dirilten"),
    Esma("الْمُحْيِي",     "el-Muhyî",      "Hayat veren"),
    Esma("الْمُمِيتُ",     "el-Mümît",      "Ölümü yaratan"),
    Esma("الْحَيُّ",       "el-Hayy",       "Diri, her zaman var"),
    Esma("الْقَيُّومُ",    "el-Kayyûm",     "Varlığı kendinden olan"),
    Esma("الْوَاجِدُ",     "el-Vâcid",      "Hiçbir şeyi olmayan"),
    Esma("الْمَاجِدُ",     "el-Mâcid",      "Şan ve şeref sahibi"),
    Esma("الْواحِدُ",      "el-Vâhid",      "Tek, bir"),
    Esma("الْأَحَدُ",      "el-Ehad",       "Eşsiz, benzersiz bir"),
    Esma("الصَّمَدُ",      "es-Samed",      "Her şey Ona muhtaç"),
    Esma("الْقَادِرُ",     "el-Kâdir",      "Gücü yeten"),
    Esma("الْمُقْتَدِرُ",  "el-Muktedir",   "Gücü her şeye yeten"),
    Esma("الْمُقَدِّمُ",   "el-Mukaddim",   "Öne geçiren"),
    Esma("الْمُؤَخِّرُ",   "el-Muahhir",    "Geri bırakan"),
    Esma("الأَوَّلُ",      "el-Evvel",      "İlk olan"),
    Esma("الآخِرُ",        "el-Âhir",       "Son olan"),
    Esma("الظَّاهِرُ",     "ez-Zâhir",      "Aşikar olan"),
    Esma("الْبَاطِنُ",     "el-Bâtın",      "Gizli olan"),
    Esma("الْوَالِي",      "el-Vâlî",       "Her şeyi idare eden"),
    Esma("الْمُتَعَالِي",  "el-Müteâlî",    "Yüceliğinde sınır olmayan"),
    Esma("الْبَرُّ",       "el-Berr",       "İyiliği çok olan"),
    Esma("التَّوَّابُ",    "et-Tevvâb",     "Tevbeleri kabul eden"),
    Esma("الْمُنْتَقِمُ",  "el-Müntekim",   "Adaleti uygulayan"),
    Esma("الْعَفُوُّ",     "el-Afüvv",      "Affeden"),
    Esma("الرَّؤُوفُ",     "er-Raûf",       "Çok şefkatli"),
    Esma("مَالِكُ الْمُلْكِ", "Mâlikü'l-Mülk", "Mülkün gerçek sahibi"),
    Esma("ذُوالْجَلاَلِ وَالإِكْرَامِ", "Zü'l-Celâli ve'l-İkrâm", "Celal ve ikram sahibi"),
    Esma("الْمُقْسِطُ",    "el-Muksit",     "Adaletle hükmeden"),
    Esma("الْجَامِعُ",     "el-Câmi'",      "Her şeyi bir araya toplayan"),
    Esma("الْغَنِيُّ",     "el-Ganiyy",     "Her şeyden müstağni"),
    Esma("الْمُغْنِي",     "el-Mugnî",      "Zengin eden"),
    Esma("الْمَانِعُ",     "el-Mâni'",      "Engelleyen"),
    Esma("الضَّارُ",       "ed-Dârr",       "Zarar veren"),
    Esma("النَّافِعُ",     "en-Nâfi'",      "Fayda veren"),
    Esma("النُّورُ",       "en-Nûr",        "Aydınlatan"),
    Esma("الْهَادِي",      "el-Hâdî",       "Hidayet veren"),
    Esma("الْبَدِيعُ",     "el-Bedî'",      "Eşsiz yaratan"),
    Esma("الْبَاقِي",      "el-Bâkî",       "Varlığı sonsuz"),
    Esma("الْوَارِثُ",     "el-Vâris",      "Her şeyin gerçek mirasçısı"),
    Esma("الرَّشِيدُ",     "er-Reşîd",      "Doğru yolu gösteren"),
    Esma("الصَّبُورُ",     "es-Sabûr",      "Sabırlı olan")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsmaUlHusnaScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Esmâ-ül Hüsnâ") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            itemsIndexed(ESMA_UL_HUSNA) { i, esma ->
                IslamicCard(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text  = "${i + 1}.",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text(esma.turkce,  style = MaterialTheme.typography.titleSmall, color = GoldAccent)
                            }
                            Text(esma.anlami, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            text      = esma.arapca,
                            style     = ArabicTextMedium.copy(fontSize = 20.sp),
                            color     = ArabesqueGold,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}

private val Int.dp get() = androidx.compose.ui.unit.Dp(this.toFloat())
private val Int.sp get() = androidx.compose.ui.unit.TextUnit(this.toFloat(), androidx.compose.ui.unit.TextUnitType.Sp)
