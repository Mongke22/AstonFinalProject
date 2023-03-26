package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetCharactersByEpisodeUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(charactersUrl: List<String>) = repository.getCharactersByEpisode(charactersUrl)
}