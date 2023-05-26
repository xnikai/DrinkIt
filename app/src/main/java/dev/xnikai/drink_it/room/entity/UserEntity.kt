package dev.xnikai.drink_it.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val name: String,
    var needToDrink: Int,
    var drinkCount: Int,
)
