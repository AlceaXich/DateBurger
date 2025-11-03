package com.example.dateburger.ui.screen.portada

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PortadaViewModel : ViewModel() {
    private val _detailEvent = MutableStateFlow(
        Event(
            title = "Intercambio de Hamburguesas",
            date = "Sábado 14 de Diciembre, 6:00 pm",
            place = "Casa de Lu",
            description = "Trae tu media XD"
        )
    )
    val detailEvent: StateFlow<Event> = _detailEvent
}

data class Event(
    val title: String,
    val date: String,
    val place: String,
    val description: String
)