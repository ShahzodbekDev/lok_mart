package com.example.locmart.domain.model

sealed class Destination {
    object OnBoarding : Destination()
    object Home : Destination()

    object Auth : Destination()
}