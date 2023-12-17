package com.example.lordoftheringsmoviesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lordoftheringsmoviesapp.ui.theme.LordOfTheRIngsMoviesAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.AsyncImage
import com.example.lordoftheringsmoviesapp.repository.Movie
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            LordOfTheRIngsMoviesAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF292929)) {

                    MainView(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val uiState by viewModel.immutableMovieData.observeAsState(UiState())

    when {
        uiState.isLoading -> { MyLoadingView() }

        uiState.error != null -> { MyErrorView(uiState.error) }

        uiState.data != null -> { uiState.data?.let { DisplayMovies(movies = it) } }
    }
}

@Composable
fun MyErrorView(errorMessage: String? = "") {
        Text(
            text = "Error in fetching data: $errorMessage",
            color = Color.Red,
            fontSize =19.sp,
            fontStyle = FontStyle.Italic,
        )
}

@Composable
fun MyLoadingView() {
        CircularProgressIndicator(
            modifier = Modifier.width(20.dp),
            color = Color.White,

        )
}


@Composable
fun DisplayMovies(movies: List<Movie>) {
    val images = arrayOf(
        R.drawable.unexpected_journey,
        R.drawable.desolation_of_smaug,
        R.drawable.battle_of_five_armies,
        R.drawable.the_two_towers
    )

    if (movies.isNotEmpty()) {
        LazyColumn {
            items(movies) {movie ->
                Log.d("MainActivity", "${movie.name}")
                Movie(
                    name = movie.name,
                    runtimeInMinutes = movie.runtimeInMinutes,
                    rottenTomatoesScore = movie.rottenTomatoesScore,
                    boxOfficeRevenueInMillions = movie.boxOfficeRevenueInMillions,
                    image = painterResource(id = images[Random.nextInt(images.size)])
                )
            }
        }
    }
}


@Composable
fun Movie(
    name: String,
    runtimeInMinutes: Int,
    image: Painter,
    rottenTomatoesScore: Double,
    boxOfficeRevenueInMillions: Double
) {
    val hours: Int = runtimeInMinutes/60
    val minutes: Int = runtimeInMinutes%60

    Column(Modifier.padding(all = 5.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF222222))
        ) {
            Row(Modifier
                .padding(all = 5.dp)
            ) {
                Image(
                    modifier = Modifier.width(100.dp),
                    painter = image,
                    contentDescription = null
                )
                Column(Modifier.padding(start = 5.dp)) {
                    Text(
                        text = "\"$name\"",
                        fontSize =19.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )


                    Row {
                        Spacer(
                            modifier = Modifier.size(12.dp),
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "Rotten Tomatoes Score",
                            fontSize =10.sp,
                            color = Color.Gray
                        )
                    }
                    Row (){
                        Image(
                            modifier = Modifier.width(16.dp),
                            painter = painterResource(R.drawable.rotten_tomatoes),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "$rottenTomatoesScore %",
                            fontSize =15.sp,
                            color = Color.White
                        )

                    }

                    Row {
                        Spacer(
                            modifier = Modifier.size(12.dp),
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "Duration",
                            fontSize =10.sp,
                            color = Color.Gray
                        )
                    }
                    Row (){
                        Image(
                            modifier = Modifier.width(16.dp),
                            painter = painterResource(R.drawable.clock),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "${hours}h ${minutes}min",
                            fontSize =15.sp,
                            color = Color.White
                        )
                    }

                    Row {
                        Spacer(
                            modifier = Modifier.size(12.dp),
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "Box Office Revenue",
                            fontSize =10.sp,
                            color = Color.Gray
                        )
                    }
                    Row (){
                        Image(
                            modifier = Modifier.width(16.dp),
                            painter = painterResource(R.drawable.dollar),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "$boxOfficeRevenueInMillions Million USD",
                            fontSize =15.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

}
