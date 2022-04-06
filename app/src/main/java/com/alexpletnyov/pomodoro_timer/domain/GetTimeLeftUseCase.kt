package com.alexpletnyov.pomodoro_timer.domain

import androidx.lifecycle.LiveData

class GetTimeLeftUseCase(private val pomodoroTimerRepository: PomodoroTimerRepository) {

	fun getTimeLeft(): LiveData<Long> {
		return pomodoroTimerRepository.getTimeLeft()
	}
}