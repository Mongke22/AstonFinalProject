package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetCharacterInfoUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCharacterInfo(id)
}