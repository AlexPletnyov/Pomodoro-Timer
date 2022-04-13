package com.alexpletnyov.pomodoro_timer.domain

class PauseTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun pauseTimer() {
		pomodoroTimerRepository.pauseTimer()
	}
}