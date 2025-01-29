package com.example.androidplayground.ui.navigation

sealed interface Destination {
    val route: String
    val title: String
}

data object Home : Destination {
    override val route = "home"
    override val title = "Home"
}

data object Brush : Destination {
    override val route = "brush"
    override val title = "Brush"
}
