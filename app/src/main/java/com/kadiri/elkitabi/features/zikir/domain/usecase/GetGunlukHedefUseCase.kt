package com.kadiri.elkitabi.features.zikir.domain.usecase

import com.kadiri.elkitabi.features.zikir.domain.model.ZikirHedef
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import javax.inject.Inject

class GetGunlukHedefUseCase @Inject constructor(
    private val repository: ZikirRepository
) {
    suspend operator fun invoke(zikirId: Int): ZikirHedef? = repository.getHedef(zikirId)
}
