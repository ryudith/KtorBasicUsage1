package com.ryudith.ktorbasicusage1.service

@kotlinx.serialization.Serializable
data class ListMovieEntity(
    val code: Int = 0,
    val message: String? = null,
    val page: Int = 1,
    val limit: Int = 10,
    val data: List<MovieEntity> = listOf(),
    val debugData: String? = null,
    val session: String? = null,
)
