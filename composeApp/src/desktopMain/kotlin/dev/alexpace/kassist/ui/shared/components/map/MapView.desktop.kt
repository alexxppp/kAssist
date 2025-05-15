package dev.alexpace.kassist.ui.shared.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.mmoczkowski.chart.Chart
import com.mmoczkowski.chart.cache.impl.lru.rememberLruCache
import com.mmoczkowski.chart.provider.api.TileCoords
import com.mmoczkowski.chart.provider.impl.osm.rememberOpenStreetMapTileProvider
import dev.alexpace.kassist.domain.models.shared.MapMarker

@Composable
actual fun MapView(
    markers: List<MapMarker>,
    initialLat: Double?,
    initialLon: Double?,
    zoomLevel: Float
) {
    val cache = rememberLruCache<Pair<Int, TileCoords>, ImageBitmap>(maxSize = 150)
    val provider = rememberOpenStreetMapTileProvider()
    Chart(
        provider = provider,
        cache = cache,
        modifier = Modifier.fillMaxSize()
    )
}