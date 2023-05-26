package dev.xnikai.drink_it.state

import dev.xnikai.drink_it.room.entity.UserEntity

data class UserState(
    val userEntity: UserEntity? = null,
)
