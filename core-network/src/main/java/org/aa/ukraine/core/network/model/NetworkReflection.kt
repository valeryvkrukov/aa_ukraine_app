package org.aa.ukraine.core.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class NetworkReflection(
    val title: String,
    val quote: String,
    val quoteSource: String,
    val commentary: String
)
