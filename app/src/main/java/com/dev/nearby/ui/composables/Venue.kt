package com.dev.nearby.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.nearby.data.local.VenueEntity
import com.dev.nearby.data.models.Venue

@Composable
fun Venue(venue: VenueEntity, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = venue.name, fontWeight = FontWeight.Bold)
        Text(text = venue.address ?: "", fontWeight = FontWeight.Normal)
    }
}