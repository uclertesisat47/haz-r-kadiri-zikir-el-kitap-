package com.kadiri.elkitabi.features.zikir.domain.usecase

import com.kadiri.elkitabi.features.zikir.domain.model.Zikir
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetZikirListeUseCase @Inject constructor(
    private val repository: ZikirRepository
) {
    operator fun invoke(): Flow<List<Zikir>> = repository.getAllZikirler()
}
