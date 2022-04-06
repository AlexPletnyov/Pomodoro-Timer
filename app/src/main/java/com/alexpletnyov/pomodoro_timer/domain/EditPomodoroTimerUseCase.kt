package com.alexpletnyov.pomodoro_timer.domain

class EditPomodoroTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun editPomodoroTimer(pomodoroTimer: PomodoroTimer) {
		pomodoroTimerRepository.editPomodoroTimer(pomodoroTimer)
	}
}