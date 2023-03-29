package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetEpisodesListUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    operator fun invoke() = repository.getEpisodesList()
}