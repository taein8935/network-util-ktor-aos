package com.tikim.networkutilktor.animal.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class AnimalDto(
    val id: Int,
    val name: String,
    val species: String,
    val family: String,
    val habitat: String,
    val place_of_found: String,
    val diet: String,
    val description: String,
    val weight_kg: Double,
    val height_cm: Double,
    val image: String
)