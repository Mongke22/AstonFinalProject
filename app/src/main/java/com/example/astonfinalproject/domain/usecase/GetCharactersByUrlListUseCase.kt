package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetCharactersByUrlListUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(charactersUrl: List<String>) = repository.getCharactersByUrlList(charactersUrl)
}