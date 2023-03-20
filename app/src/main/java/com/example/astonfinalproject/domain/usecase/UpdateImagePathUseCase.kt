package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class UpdateImagePathUseCase(
    private val repository: LogicRepository
) {
    suspend operator fun invoke(id: Int, path: String) = repository.updateCharacterImagePath(id, path)
}