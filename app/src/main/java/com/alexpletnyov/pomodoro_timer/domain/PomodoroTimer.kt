package com.alexpletnyov.pomodoro_timer.domain

data class PomodoroTimer(
	val pomodoroTime: Long,
	val breakTime: Long,
	val longBreakTime: Long,
	val pomodoroUntilLongBreak: Int
)
