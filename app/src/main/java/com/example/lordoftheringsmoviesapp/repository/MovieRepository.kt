package com.example.lordoftheringsmoviesapp.repository
import retrofit2.Response

class MovieRepository {
    suspend fun getMoveResponse(): Response<MovieResponse> = MovieService.movieService.getMovieResponse()

    suspend fun getMovieDetailsResponse(id: String): Response<MovieResponse> = MovieService.movieService.getMovieDetailsResponse(id)
}