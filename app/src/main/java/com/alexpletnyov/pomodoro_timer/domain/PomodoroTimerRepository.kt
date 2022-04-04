package com.alexpletnyov.pomodoro_timer.domain

interface PomodoroTimerRepository {

	fun startTimer(timeLength: Long)
	fun pauseTimer()
	fun resumeTimer()
	fun stopTimer()
	fun getPomodoroTimer(): PomodoroTimer
}