package com.example.player_screen.domain


interface IVideoRepository {
    suspend fun getVideos(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): Result<List<VideoItem>>
}