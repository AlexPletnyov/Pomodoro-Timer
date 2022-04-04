package com.alexpletnyov.pomodoro_timer.domain

class StopTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun stopTimer() {
		pomodoroTimerRepository.stopTimer()
	}
}