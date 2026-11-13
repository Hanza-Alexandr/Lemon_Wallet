package com.example.bdui_screen.domain

import kotlinx.coroutines.flow.Flow

interface IBDUIRepository {
    fun observePromoScreen(): Flow<List<BDUIComponent>>
}