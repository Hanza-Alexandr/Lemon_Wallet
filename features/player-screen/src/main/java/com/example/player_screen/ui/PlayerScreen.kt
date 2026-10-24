package com.example.player_screen.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.player_screen.domain.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(){

}

/**
 * Есть сущнось видео
 *      - URL видео
 *      - Первый кадр видео
 * Есть сущность ссесия:
 *      - Хранит историю просмотра
 *      - Кеш
 *          - Кеш видео
 *          - Лайки
 *      - Текуший ролик
 *
 * Репозиторий. Поставляет данные для ссесии, инициализируется при при создании ссессии.
 *
 * Апи с видео:
 * - отдается список видеороликов. В нем вся метадата и ссылка на файл
 *
 * т.е. будет доменная сущность
 *
 */




@HiltViewModel
class VM(): ViewModel(){

    private val _state = MutableStateFlow(ShortPlayerScreenState())
    //
    val isLoadingListVideo: Boolean = false
    val isLoadingCurrentVideo: Boolean = false

    init{
        //Если VM очищать от мусора то тут нужно запускать корутину которая крутится постоянно и обновляет данные т.к если тут при вызове nextvideo будет постоянно запускатсья кортуина и запускатся вычисления будут фризы

    }
    fun onNextVideo(){
        viewModelScope.launch {

        }
    }
    fun onBackVideo(){

    }
    fun onLikeVideo(){

    }
}

data class ShortPlayerScreenState(
    val currentVideo: VideoUiState = VideoUiState.Loading,
    val isLiked: Boolean = false,
)

sealed class VideoUiState{
    object Loading: VideoUiState()
    data class Success(val video: VideoItem): VideoUiState()
    data class Error(val message: String): VideoUiState()
}










