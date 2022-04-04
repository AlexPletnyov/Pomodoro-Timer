package com.alexpletnyov.pomodoro_timer.domain

class GetPomodoroTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun getPomodoroTimer(): PomodoroTimer {
		return pomodoroTimerRepository.getPomodoroTimer()
	}
}