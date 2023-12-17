package com.example.lordoftheringsmoviesapp.repository
import retrofit2.Response

class MovieRepository {
    suspend fun getMoveResponse(): Response<MovieResponse> = MovieService.movieService.getMovieResponse()
}