package com.example.lordoftheringsmoviesapp.repository

data class Movie(
    val _id: String,
    val name: String,
    val runtimeInMinutes: Int,
    val budgetInMillions: Int,
    val boxOfficeRevenueInMillions: Double,
    val academyAwardNominations: Int,
    val academyAwardWins: Int,
    val rottenTomatoesScore: Double
)

