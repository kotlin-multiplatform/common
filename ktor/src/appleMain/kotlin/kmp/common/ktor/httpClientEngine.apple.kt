package kmp.common.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

public actual fun httpClientEngine(): HttpClientEngine = Darwin.create()