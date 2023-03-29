package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository
import javax.inject.Inject

class GetLocationsListUseCase @Inject constructor(
    private val repository: LogicRepository
) {
    operator fun invoke() = repository.getLocationsList()
}