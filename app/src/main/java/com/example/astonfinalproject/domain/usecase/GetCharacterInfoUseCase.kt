package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetCharacterInfoUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(url: String) = repository.getCharacterInfo(url)
}