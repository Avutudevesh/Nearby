package com.dev.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dev.nearby.data.local.VenueEntity
import com.dev.nearby.ui.composables.PageError
import com.dev.nearby.ui.composables.PageLoading
import com.dev.nearby.ui.composables.Venue
import com.dev.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NearbyTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val venues = viewModel.pageData.collectAsLazyPagingItems()
                    Column {
                        NearbyContainer(venues = venues, modifier = Modifier.weight(1f))
                        FilterContainer(onFilterChange = {
                            viewModel.onDistanceFilterChange(it)
                        })
                    }
                }
            }
        }
    }

    @Composable
    private fun NearbyContainer(venues: LazyPagingItems<VenueEntity>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            items(venues.itemCount, key = venues.itemKey { it.id }) { index ->
                venues[index]?.let {
                    Venue(venue = it)
                }
            }
            item {
                PageLoading(loadState = venues.loadState.mediator)
                PageError(venues = venues)
            }
        }
    }

    @Composable
    fun FilterContainer(onFilterChange: (value: Float) -> Unit, modifier: Modifier = Modifier) {
        val sliderPosition by viewModel.distanceFilterState.collectAsState()
        Column(
            modifier = modifier
        ) {
            Slider(
                value = sliderPosition,
                onValueChange = {
                    onFilterChange.invoke(it)
                }
            )
            val distance = if (sliderPosition == 0f) {
                MainViewModel.MIN_DISTANCE
            } else {
                (sliderPosition * MainViewModel.MAX_DISTANCE).toInt()
            }
            Text(text = "Showing venues within $distance miles")
        }
    }
}