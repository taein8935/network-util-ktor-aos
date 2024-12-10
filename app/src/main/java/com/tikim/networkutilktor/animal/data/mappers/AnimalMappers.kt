package com.tikim.networkutilktor.animal.data.mappers


import com.tikim.networkutilktor.animal.data.dto.AnimalDto
import com.tikim.networkutilktor.animal.domain.Animal


fun AnimalDto.toAnimal(): Animal {
    return Animal(
        id = id,
        name = name,
        species = species,
        family = family,
        habitat = habitat,
        placeOfFound = place_of_found
    )
}