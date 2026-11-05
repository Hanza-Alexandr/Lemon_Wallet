package com.example.player_screen.domain

data class VideoItem(
    val id: Long,
    val durationSeconds: Int,
    val tags: List<String>,
    val authorName: String,
    val previewThumbnailUrl: String,
    val videoPlayUrl: String,
    val width: Int,
    val height: Int
)