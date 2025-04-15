package dev.alexpace.kassist.data.network.http

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClient(): HttpClientEngine = Darwin.create()
