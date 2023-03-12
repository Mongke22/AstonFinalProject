package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetEpisodesListUseCase(
    private val repository: LogicRepository
) {
    operator fun invoke() = repository.getEpisodesList()
}