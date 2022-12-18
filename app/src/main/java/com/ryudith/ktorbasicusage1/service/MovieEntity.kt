package com.ryudith.ktorbasicusage1.service

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieEntity(
    @SerialName("movie_id")
    val id: Int = 0,
    val name: String? = null,
    val year: Int? = null,
)
