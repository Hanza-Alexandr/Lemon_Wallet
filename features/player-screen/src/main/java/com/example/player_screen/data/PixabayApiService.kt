package com.example.player_screen.data

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("api/videos/")
    suspend fun searchVideos(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("video_type") videoType: String = "film",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
        @Query("safesearch") safeSearch: Boolean = true
    ): PixabayVideoResponse
}

@Serializable
data class PixabayVideoResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<VideoHit>
)

@Serializable
data class VideoHit(
    val id: Long,
    val duration: Int,
    val tags: String,
    val user: String,
    val videos: VideoStreams
)

@Serializable
data class VideoStreams(
    val large: VideoStreamDetails? = null,
    val medium: VideoStreamDetails? = null,
    val small: VideoStreamDetails? = null,
    val tiny: VideoStreamDetails? = null
)

@Serializable
data class VideoStreamDetails(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Long,
    val thumbnail: String
)