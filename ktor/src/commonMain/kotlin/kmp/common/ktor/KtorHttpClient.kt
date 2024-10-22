package kmp.common.ktor

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.utils.io.core.*

public abstract class KtorHttpClient(
    private val engine: HttpClientEngine = httpClientEngine()
) : Closeable {

    private var internalHttpClient: HttpClient? = null

    public val httpClient: HttpClient
        get() = internalHttpClient ?: HttpClient(engine, configureHttpClient).also {
            internalHttpClient = it
        }

    override fun close() {
        internalHttpClient?.close()
        internalHttpClient = null
    }

    public abstract val configureHttpClient: HttpClientConfig<*>.() -> Unit
}