package com.dev.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.nearby.ui.composables.VenueList
import com.dev.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchNearByLocations(12.971599, 77.594566)
        setContent {
            NearbyTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NearbyContainer()
                }
            }
        }
    }

    @Composable
    private fun NearbyContainer(modifier: Modifier = Modifier) {
        val state by viewModel.uiState.collectAsState()
        Box(modifier = modifier.fillMaxSize()) {
            when (val uiState = state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is UiState.Error -> {
                    Text(text = "Sorry, something is wrong", modifier = Modifier.align(Alignment.Center))
                }

                is UiState.Success -> {
                    VenueList(venues = uiState.venues)
                }
            }
        }
    }
}