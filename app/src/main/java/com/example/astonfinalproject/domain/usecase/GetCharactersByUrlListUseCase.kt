package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetCharactersByUrlListUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(charactersUrl: List<String>) = repository.getCharactersByUrlList(charactersUrl)
}