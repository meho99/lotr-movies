package com.example.lordoftheringsmoviesapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lordoftheringsmoviesapp.repository.Movie
import com.example.lordoftheringsmoviesapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val moveRepository = MovieRepository()

    private val mutableMovieData = MutableLiveData<UiState<List<Movie>>>()
    val immutableMovieData: LiveData<UiState<List<Movie>>> = mutableMovieData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableMovieData.postValue(UiState(isLoading = true))
            try {
                val request = moveRepository.getMoveResponse()
                Log.e("MainViewModel", "Response code ${request.code()}" )
                val movies = request.body()?.docs ?: emptyList()
                if(request.isSuccessful) {
                    mutableMovieData.postValue(UiState(data = movies))
                } else {
                    mutableMovieData.postValue(UiState(error = "Error: ${request.code()}"))
                }
            } catch(e: Exception) {
                mutableMovieData.postValue(UiState(error = e.message))
                Log.e("MainViewModel", "Error", e)
            }
        }
    }
}