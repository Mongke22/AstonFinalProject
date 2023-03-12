package com.example.astonfinalproject.domain.usecase

import com.example.astonfinalproject.domain.LogicRepository

class GetCharactersListUseCase(
    private val repository: LogicRepository
) {
    operator fun invoke() = repository.getCharactersList()
}