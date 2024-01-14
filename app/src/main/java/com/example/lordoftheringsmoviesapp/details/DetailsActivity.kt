package com.example.lordoftheringsmoviesapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lordoftheringsmoviesapp.DisplayMovies
import com.example.lordoftheringsmoviesapp.MainViewModel
import com.example.lordoftheringsmoviesapp.Movie
import com.example.lordoftheringsmoviesapp.MyErrorView
import com.example.lordoftheringsmoviesapp.MyLoadingView
import com.example.lordoftheringsmoviesapp.R
import com.example.lordoftheringsmoviesapp.UiState
import com.example.lordoftheringsmoviesapp.repository.Movie
import com.example.lordoftheringsmoviesapp.ui.theme.LordOfTheRIngsMoviesAppTheme
import kotlin.random.Random

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("CUSTOM_KEY")
        if (id != null) {
            Toast.makeText(this, "details id: $id", Toast.LENGTH_SHORT).show()
            viewModel.getData(id)
        } else {
            Toast.makeText(this, "Object id not defined", Toast.LENGTH_SHORT).show()
        }

        setContent {
            LordOfTheRIngsMoviesAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF292929)) {

                    DetailsView(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsView(viewModel: DetailsViewModel) {
    val uiState by viewModel.immutableMovieData.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }

        uiState.error != null -> {
            MyErrorView(uiState.error)
        }

        uiState.data != null -> {
            uiState.data?.let {
                DisplayMovieDetails(
                    id = it._id,
                    name = it.name,
                    runtimeInMinutes = it.runtimeInMinutes,
                    rottenTomatoesScore = it.rottenTomatoesScore,
                    boxOfficeRevenueInMillions = it.boxOfficeRevenueInMillions
                )
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(
    id: String,
    name: String,
    runtimeInMinutes: Int,
    rottenTomatoesScore: Double,
    boxOfficeRevenueInMillions: Double,
) {

    val hours: Int = runtimeInMinutes/60
    val minutes: Int = runtimeInMinutes%60


    Column(
        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Text(
            text = "Movie Details: $id",
            fontSize =15.sp,
            fontStyle = FontStyle.Italic,
            color = Color.White,
        )

        Spacer(
            modifier = Modifier.size(10.dp),
        )

        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF222222))
        ) {
            Row(Modifier
                .padding(all = 5.dp)
            ) {
                Column(Modifier.padding(start = 5.dp)) {

                    Text(
                        text = "\"$name\"",
                        fontSize =30.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                    )
                    Spacer(
                        modifier = Modifier.size(20.dp),
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
                            modifier = Modifier.width(18.dp),
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
                            modifier = Modifier.width(20.dp),
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
                            modifier = Modifier.width(20.dp),
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

