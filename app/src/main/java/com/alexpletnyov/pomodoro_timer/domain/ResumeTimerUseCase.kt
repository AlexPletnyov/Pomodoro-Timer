package com.alexpletnyov.pomodoro_timer.domain

class ResumeTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun resumeTimer() {
		pomodoroTimerRepository.resumeTimer()
	}
}