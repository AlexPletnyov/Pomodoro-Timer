package com.alexpletnyov.pomodoro_timer.domain

import androidx.lifecycle.LiveData

interface PomodoroTimerRepository {

	fun startTimer(timeLength: Long)
	fun pauseTimer()
	fun resumeTimer()
	fun stopTimer()
	fun getPomodoroTimer(): LiveData<PomodoroTimer>
	fun editPomodoroTimer(pomodoroTimer: PomodoroTimer)
	fun getTimeLeft(): LiveData<Long>
}