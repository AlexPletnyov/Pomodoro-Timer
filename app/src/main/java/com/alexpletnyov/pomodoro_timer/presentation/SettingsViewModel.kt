package com.alexpletnyov.pomodoro_timer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexpletnyov.pomodoro_timer.data.PomodoroTimerRepositoryImpl
import com.alexpletnyov.pomodoro_timer.domain.EditPomodoroTimerUseCase
import com.alexpletnyov.pomodoro_timer.domain.GetPomodoroTimerUseCase
import com.alexpletnyov.pomodoro_timer.domain.PomodoroTimer
import java.lang.Exception

class SettingsViewModel : ViewModel() {

	private val repository = PomodoroTimerRepositoryImpl

	private val editPomodoroTimerUseCase = EditPomodoroTimerUseCase(repository)
	private val getPomodoroTimerUseCase = GetPomodoroTimerUseCase(repository)

	private val _errorInputPomodoro = MutableLiveData<Boolean>()
	val errorInputPomodoro: LiveData<Boolean>
		get() = _errorInputPomodoro

	private val _errorInputBreak = MutableLiveData<Boolean>()
	val errorInputBreak: LiveData<Boolean>
		get() = _errorInputBreak

	private val _errorInputLongBreak = MutableLiveData<Boolean>()
	val errorInputLongBreak: LiveData<Boolean>
		get() = _errorInputLongBreak

	private val _errorInputPomodoroUntilLongBreak = MutableLiveData<Boolean>()
	val errorInputPomodoroUntilLongTime: LiveData<Boolean>
		get() = _errorInputPomodoroUntilLongBreak

	private val _shouldCloseScreen = MutableLiveData<Unit>()
	val shouldCloseScreen: LiveData<Unit>
		get() = _shouldCloseScreen

	lateinit var pomodoroTimer: LiveData<PomodoroTimer>

	fun getPomodoroTimer() {
		val timer = getPomodoroTimerUseCase.getPomodoroTimer()
		pomodoroTimer = timer
	}

	fun editPomodoroTimer(
		inputPomodoro: String?,
		inputBreak: String?,
		inputLongBreak: String?,
		inputPomodoroUntilLongBreak: String?
	) {
		val pomodoroTime = parseTime(inputPomodoro)
		val breakTime = parseTime(inputBreak)
		val longBreakTime = parseTime(inputLongBreak)
		val pomodoroUntilLongBreak = parseCount(inputPomodoroUntilLongBreak)
		val fieldValid = validateInput(pomodoroTime, breakTime, longBreakTime, pomodoroUntilLongBreak)
		if (fieldValid) {
			pomodoroTimer.value?.let {
				val timer = PomodoroTimer(pomodoroTime, breakTime, longBreakTime, pomodoroUntilLongBreak)
				editPomodoroTimerUseCase.editPomodoroTimer(timer)
				_shouldCloseScreen.value = Unit
			}
		}
	}

	private fun parseTime(inputTime: String?): Long {
		val time = try {
			inputTime?.replace(" ", "")?.toLong() ?: 1
		} catch (e: Exception) {
			1
		}
		return time * MINUTE_TO_MILLISECOND
	}

	private fun parseCount(inputTime: String?): Int {
		return try {
			inputTime?.replace(" ", "")?.toInt() ?: 1
		} catch (e: Exception) {
			1
		}
	}

	private fun validateInput(
		pomodoroTime: Long,
		breakTime: Long,
		longBreakTime: Long,
		pomodoroUntilLongBreak: Int
	): Boolean {
		var result = true
		if (pomodoroTime <= 0) {
			_errorInputPomodoro.value = true
			result = false
		}
		if (breakTime <= 0) {
			_errorInputBreak.value = true
			result = false
		}
		if (longBreakTime <= 0) {
			_errorInputLongBreak.value = true
			result = false
		}
		if (pomodoroUntilLongBreak <= 0) {
			_errorInputPomodoroUntilLongBreak.value = true
			result = false
		}
		return result
	}

	fun resetErrorInputPomodoro() {
		_errorInputPomodoro.value = false
	}

	fun resetErrorInputBreak() {
		_errorInputBreak.value = false
	}

	fun resetErrorInputLongBreak() {
		_errorInputLongBreak.value = false
	}

	fun resetErrorInputPomodoroUntilLongBreak() {
		_errorInputPomodoroUntilLongBreak.value = false
	}

	companion object {
		const val MINUTE_TO_MILLISECOND = 60_000L
	}
}