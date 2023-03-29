package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetLocationInfoUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(id: Int) = repository.getLocationInfo(id)

    suspend operator fun invoke(place: String) = repository.getLocationInfo(place)
}