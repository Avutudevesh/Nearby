package com.dev.nearby.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev.nearby.data.models.Venue

@Composable
fun VenueList(venues: List<Venue>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(venues.size) { index ->
            Venue(venue = venues[index])
        }
    }
}