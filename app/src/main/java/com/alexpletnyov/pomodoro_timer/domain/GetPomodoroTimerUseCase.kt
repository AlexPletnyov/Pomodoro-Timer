package com.alexpletnyov.pomodoro_timer.domain

import androidx.lifecycle.LiveData

class GetPomodoroTimerUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun getPomodoroTimer(): LiveData<PomodoroTimer> {
		return pomodoroTimerRepository.getPomodoroTimer()
	}
}