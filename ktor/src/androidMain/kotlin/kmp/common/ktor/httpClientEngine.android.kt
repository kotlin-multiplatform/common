package kmp.common.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

public actual fun httpClientEngine(): HttpClientEngine = OkHttp.create()