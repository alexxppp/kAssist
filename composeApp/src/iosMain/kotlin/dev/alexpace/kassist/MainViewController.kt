package dev.alexpace.kassist

import androidx.compose.ui.window.ComposeUIViewController
import dev.alexpace.kassist.data.utils.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }