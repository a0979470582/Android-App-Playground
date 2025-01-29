package com.example.androidplayground.ui.home

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.androidplayground.ui.navigation.Brush

@Composable
fun HomeScreen(onClickBrush: () -> Unit) {
    TextButton(onClickBrush) {
        Text(Brush.title)
    }
}
