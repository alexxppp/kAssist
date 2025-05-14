package dev.alexpace.kassist.ui.shared.pages.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.mmoczkowski.chart.Chart
import com.mmoczkowski.chart.cache.impl.lru.rememberLruCache
import com.mmoczkowski.chart.provider.api.TileCoords
import com.mmoczkowski.chart.provider.impl.osm.rememberOpenStreetMapTileProvider

@Composable
actual fun MapPage(markerTitle: String?, lat: Double?, lon: Double?) {
    val cache = rememberLruCache<Pair<Int, TileCoords>, ImageBitmap>(maxSize = 150)
    val provider = rememberOpenStreetMapTileProvider()
    Chart(
        provider = provider,
        cache = cache,
        modifier = Modifier.fillMaxSize()
    )
}