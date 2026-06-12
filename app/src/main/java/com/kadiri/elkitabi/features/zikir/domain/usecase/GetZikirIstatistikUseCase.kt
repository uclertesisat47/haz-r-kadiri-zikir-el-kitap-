package com.kadiri.elkitabi.features.zikir.domain.usecase

import com.kadiri.elkitabi.features.zikir.domain.model.ZikirIstatistik
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import javax.inject.Inject

class GetZikirIstatistikUseCase @Inject constructor(
    private val repository: ZikirRepository
) {
    suspend operator fun invoke(): ZikirIstatistik = repository.getIstatistik()
}
