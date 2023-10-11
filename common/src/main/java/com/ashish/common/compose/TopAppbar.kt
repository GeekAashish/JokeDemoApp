package com.ashish.common.compose

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ApplicationAppbar(title: String) {
    TopAppBar(
        title = {
            Text(text = title, color = Color.Green)
        }
    )
}

@Composable
fun ApplicationAppbar(
    title: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = modifier.clickable {
                    onClickBack()
                }
            )
        }
    )
}