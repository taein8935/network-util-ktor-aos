package com.tikim.networkutilktor.animal.domain

data class Animal(
    val id: Int,
    val name: String,
    val species: String,
    val family: String,
    val habitat: String,
    val placeOfFound: String,
)