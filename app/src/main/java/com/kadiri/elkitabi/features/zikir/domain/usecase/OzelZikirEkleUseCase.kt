package com.kadiri.elkitabi.features.zikir.domain.usecase

import com.kadiri.elkitabi.features.zikir.domain.model.OzelZikir
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import javax.inject.Inject

class OzelZikirEkleUseCase @Inject constructor(
    private val repository: ZikirRepository
) {
    suspend operator fun invoke(ozel: OzelZikir): Long = repository.ekleOzelZikir(ozel)
}
