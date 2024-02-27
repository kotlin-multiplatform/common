package kmp.common.ktor

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.utils.io.core.*

public abstract class KtorHttpClient(
    private val engine: HttpClientEngine = httpClientEngine()
) : Closeable {

    private var _httpClient: HttpClient? = null
    public val httpClient: HttpClient
        get() {
            if (_httpClient == null) {
                _httpClient = HttpClient(engine, setupHttpClient)
            }

            return _httpClient!!
        }

    override fun close() {
        _httpClient?.close()
        _httpClient = null
    }

    public abstract val setupHttpClient: HttpClientConfig<*>.() -> Unit
}