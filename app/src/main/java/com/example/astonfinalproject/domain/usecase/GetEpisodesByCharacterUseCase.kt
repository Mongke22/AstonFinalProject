package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetEpisodesByCharacterUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(episodesUrl: List<String>) = repository.getEpisodesByCharacter(episodesUrl)
}