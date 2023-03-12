package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetEpisodeInfoUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(url: String) = repository.getEpisodeInfo(url)
}