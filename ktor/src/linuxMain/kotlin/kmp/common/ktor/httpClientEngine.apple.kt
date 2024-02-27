package kmp.common.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.curl.Curl

public actual fun httpClientEngine(): HttpClientEngine = Curl.create()