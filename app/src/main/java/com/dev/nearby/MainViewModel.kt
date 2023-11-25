package com.dev.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dev.nearby.data.NearbyRepository
import com.dev.nearby.data.models.Venue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val nearbyRepository: NearbyRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState> = _uiState

    fun fetchNearByLocations(lat: Double, lng: Double) {
        viewModelScope.launch {
            nearbyRepository.fetchNearbyVenues(lat, lng)
                .catch {
                    _uiState.value = UiState.Error
                }
                .collectLatest {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val nearbyRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication).nearbyRepository
                MainViewModel(
                    nearbyRepository = nearbyRepository,
                )
            }
        }
    }

}

sealed class UiState {
    object Loading : UiState()

    object Error : UiState()

    data class Success(val venues: List<Venue>) : UiState()
}