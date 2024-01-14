package com.example.lordoftheringsmoviesapp.repository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieService {
    @GET("/v2/movie")
    @Headers("Authorization: Bearer arK8HgB4d1dUgl-EY-mU")
    suspend fun getMovieResponse(): Response<MovieResponse>

    @GET("/v2/movie/{id}")
    @Headers("Authorization: Bearer arK8HgB4d1dUgl-EY-mU")
    suspend fun getMovieDetailsResponse(@Path(value = "id")  id: String): Response<MovieResponse>

    companion object {
        private const val MOVIE_URL = "https://the-one-api.dev/"
        private val logger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger)
        }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(okHttp)
                .build()
        }

        val movieService: MovieService by lazy {
            retrofit.create(MovieService::class.java)
        }
    }
}