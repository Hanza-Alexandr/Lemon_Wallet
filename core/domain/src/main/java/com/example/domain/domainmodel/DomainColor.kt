package com.example.domain.domainmodel

import com.example.domain.utils.SynStatus


data class DomainColor(
    val id: String,
    val userId: String,
    val hex: String,
)

data class NewDomainColor(
    val userId: String,
    val hex: String,
)
