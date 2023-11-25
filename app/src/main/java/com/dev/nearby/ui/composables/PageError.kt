package com.dev.nearby.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.dev.nearby.data.local.VenueEntity

@Composable
fun PageError(venues: LazyPagingItems<VenueEntity>) {
    val loadState = venues.loadState.mediator
    if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
        val isPaginatingError = (loadState.append is LoadState.Error) || venues.itemCount > 1
        val error = if (loadState.append is LoadState.Error)
            (loadState.append as LoadState.Error).error
        else
            (loadState.refresh as LoadState.Error).error

        val modifier = if (isPaginatingError) {
            Modifier.padding(8.dp)
        } else {
            Modifier.fillMaxSize()
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!isPaginatingError) {
                Icon(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = error.message ?: error.toString(),
                textAlign = TextAlign.Center,
            )

            Button(
                onClick = {
                    venues.refresh()
                },
                content = {
                    Text(text = "Refresh")
                }
            )
        }
    }
}