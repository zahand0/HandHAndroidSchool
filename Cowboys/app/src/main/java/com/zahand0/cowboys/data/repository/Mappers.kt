package com.zahand0.cowboys.data.repository

import com.zahand0.cowboys.data.dto.Profile
import com.zahand0.cowboys.domain.model.User

fun Profile.toUser() =
    User(
        name = name,
        surname = surname,
        occupation = occupation,
        avatarUrl = "https://i.ibb.co/h7DVHqJ/Saitama.png"
    )