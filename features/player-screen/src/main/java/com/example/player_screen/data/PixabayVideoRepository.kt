package com.example.player_screen.data

import com.example.player_screen.domain.IVideoRepository
import com.example.player_screen.domain.VideoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PixabayVideoRepository @Inject constructor(
    private val apiService: PixabayApiService,
    private val apiKey: String,

) : IVideoRepository {

    override suspend fun getVideos(
        query: String,
        page: Int,
        perPage: Int
    ): Result<List<VideoItem>> = withContext(Dispatchers.IO) {
        runCatching {
            val response = apiService.searchVideos(
                apiKey = apiKey,
                query = query,
                page = page,
                perPage = perPage
            )

            response.hits.mapNotNull { hit ->
                hit.toDomainModel()
            }
        }
    }

    private fun VideoHit.toDomainModel(): VideoItem? {
        // Выбираем оптимальный поток: medium -> small -> large -> tiny
        val selectedStream: VideoStreamDetails = videos.medium
            ?: videos.small
            ?: videos.large
            ?: videos.tiny
            ?: return null // Пропускаем, если видеопоток отсутствует

        // Превью берем из доступного стрима
        val thumbnailUrl = selectedStream.thumbnail.ifBlank {
            videos.tiny?.thumbnail ?: videos.small?.thumbnail.orEmpty()
        }

        return VideoItem(
            id = id,
            durationSeconds = duration,
            tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            authorName = user,
            previewThumbnailUrl = thumbnailUrl,
            videoPlayUrl = selectedStream.url,
            width = selectedStream.width,
            height = selectedStream.height
        )
    }
}