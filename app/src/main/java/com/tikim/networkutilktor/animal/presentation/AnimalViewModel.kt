package com.tikim.networkutilktor.animal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikim.networkutilktor.animal.domain.AnimalRepository
import com.tikim.networkutilktor.core.domain.onError
import com.tikim.networkutilktor.core.domain.onSuccess
import kotlinx.coroutines.launch

class AnimalViewModel(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    init {
        fetchAnimal()
    }

    private fun fetchAnimal() {
        viewModelScope.launch {
            val result = animalRepository.fetchAnimal()
            result
                .onSuccess { animals ->
                    println("success size:${animals.size} $animals")
                }
                .onError { error ->
                    println("error ${error.name}")
                }
        }
    }
}