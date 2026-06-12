package com.kadiri.elkitabi.features.zikir.domain.usecase

import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSession
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import javax.inject.Inject

class SaveZikirSessionUseCase @Inject constructor(
    private val repository: ZikirRepository
) {
    suspend operator fun invoke(session: ZikirSession): Long = repository.saveSession(session)
}
