package dev.alexpace.kassist.ui.shared.utils.controllers

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SnackbarController {
    private val _snackbarEvents = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val snackbarEvents = _snackbarEvents.asSharedFlow()

    fun showSnackbar(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _snackbarEvents.emit(message)
        }
    }
}