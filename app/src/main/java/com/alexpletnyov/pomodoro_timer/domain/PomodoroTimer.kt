package com.alexpletnyov.pomodoro_timer.domain

data class PomodoroTimer(
	var pomodoroTime: Long,
	var breakTime: Long,
	var longBreakTime: Long,
	var pomodoroUntilLongBreak: Int
)