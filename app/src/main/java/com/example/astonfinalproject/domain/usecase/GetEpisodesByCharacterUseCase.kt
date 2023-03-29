package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetEpisodesByCharacterUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(episodesUrl: List<String>) = repository.getEpisodesByCharacter(episodesUrl)
}