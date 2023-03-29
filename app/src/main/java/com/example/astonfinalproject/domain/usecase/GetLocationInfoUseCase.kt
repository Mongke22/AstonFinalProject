package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetLocationInfoUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(id: Int) = repository.getLocationInfo(id)

    suspend operator fun invoke(place: String) = repository.getLocationInfo(place)
}