package kmp.common.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

public actual fun httpClientEngine(): HttpClientEngine = CIO.create()