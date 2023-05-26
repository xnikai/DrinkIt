package dev.xnikai.drink_it.repository

import dev.xnikai.drink_it.room.AppDatabase
import dev.xnikai.drink_it.room.entity.UserEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val userDao: AppDatabase.UserDao
) {
    suspend fun createUser(userEntity: UserEntity) {
        userDao.createUser(userEntity)
    }

    suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(userEntity)
    }
}