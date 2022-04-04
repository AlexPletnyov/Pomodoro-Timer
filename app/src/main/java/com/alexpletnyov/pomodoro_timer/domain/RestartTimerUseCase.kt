package com.alexpletnyov.pomodoro_timer.domain

class RestartTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun restartTimer() {
		pomodoroTimerRepository.resumeTimer()
	}
}