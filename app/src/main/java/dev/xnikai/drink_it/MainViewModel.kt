package dev.xnikai.drink_it

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xnikai.drink_it.repository.DatabaseRepository
import dev.xnikai.drink_it.room.entity.UserEntity
import dev.xnikai.drink_it.state.AlertDialogState
import dev.xnikai.drink_it.state.UserState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    var userState by mutableStateOf(UserState())
    var inputNameState by mutableStateOf("")
    var alertDialogState by mutableStateOf(AlertDialogState())

    init {
        checkUser()
    }

    private fun checkUser() {
        viewModelScope.launch {
            databaseRepository.getUser().also {
                if (it != null) {
                    userState = userState.copy(it)
                    Log.d("GET_USER", it.name)
                }
            }
        }
    }

    fun changeLogin() {
        if (inputNameState.isNotBlank()) {
            val userEntity = UserEntity(
                name = inputNameState,
                drinkCount = 0,
                needToDrink = 5
            )
            viewModelScope.launch {
                databaseRepository.createUser(userEntity)
                checkUser()
            }
        }
    }

    fun addGlass() {
        if (userState.userEntity!!.drinkCount < userState.userEntity!!.needToDrink) {
            viewModelScope.launch {
                databaseRepository.updateUser(userState.userEntity!!.copy(drinkCount = userState.userEntity!!.drinkCount + 1))
                checkUser()
            }
        }
    }

    fun addNeedToDrink(value: Int) {
        if(userState.userEntity!!.needToDrink >= 0) {
            viewModelScope.launch {
                databaseRepository.updateUser(userState.userEntity!!.copy(needToDrink = value))
                checkUser()
            }
        }
    }

    fun changeName(newValue: String) {
        inputNameState = newValue
    }

    fun resetDrinkCount() {
        viewModelScope.launch {
            databaseRepository.updateUser(
                userState.userEntity!!.copy(
                    drinkCount = 0
                )
            )
            checkUser()
        }
    }
}