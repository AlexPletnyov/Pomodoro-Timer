package com.alexpletnyov.pomodoro_timer.domain

class StartTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun startTimer(timeLength: Long) {
		pomodoroTimerRepository.startTimer(timeLength)
	}
}