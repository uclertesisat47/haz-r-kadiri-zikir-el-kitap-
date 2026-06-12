package com.kadiri.elkitabi.features.kazanamaz.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kaza_namazlari")
data class KazaNamaziEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val baslangicYili: Int,
    val toplamKazaSayisi: Int,
    val tamamlananSayisi: Int = 0,
    val gunlukHedef: Int = 1,
    val olusturmaTarihi: String
)
