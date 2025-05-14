package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.runtime.Composable

@Composable
expect fun MapView(markerTitle: String? = null, lat: Double? = null, lon: Double? = null)