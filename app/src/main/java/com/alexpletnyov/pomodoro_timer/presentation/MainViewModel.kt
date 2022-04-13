package com.alexpletnyov.pomodoro_timer.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexpletnyov.pomodoro_timer.data.PomodoroTimerRepositoryImpl
import com.alexpletnyov.pomodoro_timer.domain.*

class MainViewModel : ViewModel() {

	val repository = PomodoroTimerRepositoryImpl

	private val editPomodoroTimerUseCase = EditPomodoroTimerUseCase(repository)
	private val getPomodoroTimerUseCase = GetPomodoroTimerUseCase(repository)

	private val startTimerUseCase = StartTimerUseCase(repository)
	private val pauseTimerUseCase = PauseTimerUseCase(repository)
	private val resumeTimerUseCase = ResumeTimerUseCase(repository)
	private val stopTimerUseCase = StopTimerUseCase(repository)
	private val getTimeLeftUseCase = GetTimeLeftUseCase(repository)

	var pomodoroTimer = getPomodoroTimerUseCase.getPomodoroTimer()
	var timeLeft = getTimeLeftUseCase.getTimeLeft()

	fun start(pomodoroTimer: PomodoroTimer) {
		startTimerUseCase.startTimer(pomodoroTimer.pomodoroTime)
	}

	fun pause() {
		pauseTimerUseCase.pauseTimer()
	}

	fun resume() {
		resumeTimerUseCase.resumeTimer()
	}

	fun stop() {
		stopTimerUseCase.stopTimer()
	}

	fun getTimeLeftText(time: Long): String {
		val m = (time / 1000) / 60
		val s = (time / 1000) % 60
		return String.format("%02d:%02d", m, s)
	}

	fun changePomodoroTime(pomodoroTimer: PomodoroTimer, time: Long) {
		val newPomodoroTimer = pomodoroTimer.copy(pomodoroTime = time * MINUTE_TO_MILLISECOND)
		editPomodoroTimerUseCase.editPomodoroTimer(newPomodoroTimer)
	}

	fun changeBreakTime(pomodoroTimer: PomodoroTimer, time: Long) {
		val newPomodoroTimer = pomodoroTimer.copy(breakTime = time * MINUTE_TO_MILLISECOND)
		editPomodoroTimerUseCase.editPomodoroTimer(newPomodoroTimer)
	}

	fun changeLongBreakTime(pomodoroTimer: PomodoroTimer, time: Long) {
		val newPomodoroTimer = pomodoroTimer.copy(longBreakTime = time * MINUTE_TO_MILLISECOND)
		editPomodoroTimerUseCase.editPomodoroTimer(newPomodoroTimer)
	}

	fun changePomodoroUntilLongBreak(pomodoroTimer: PomodoroTimer, num: Int) {
		val newPomodoroTimer = pomodoroTimer.copy(pomodoroUntilLongBreak = num)
		editPomodoroTimerUseCase.editPomodoroTimer(newPomodoroTimer)
	}

	companion object {
		private const val MINUTE_TO_MILLISECOND = 60_000L
	}
}