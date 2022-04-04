package com.alexpletnyov.pomodoro_timer.data

import android.os.CountDownTimer
import com.alexpletnyov.pomodoro_timer.domain.PomodoroTimer
import com.alexpletnyov.pomodoro_timer.domain.PomodoroTimerRepository

object PomodoroTimerRepositoryImpl : PomodoroTimerRepository {

	enum class TimerType {
		POMODORO_TIMER, BREAK_TIMER, LONG_BREAK_TIMER
	}

	private const val COUNT_DOWN_INTERVAL = 1000L
	private const val DEFAULT_POMODORO_TIME = 1500_000L
	private const val DEFAULT_BREAK_TIME = 300_000L
	private const val DEFAULT_LONG_BREAK_TIME = 900_000L
	private const val DEFAULT_POMODORO_UNTIL_LONG_BREAK_TIME = 4

	private var pomodoroTimer = PomodoroTimer(
		DEFAULT_POMODORO_TIME,
		DEFAULT_BREAK_TIME,
		DEFAULT_LONG_BREAK_TIME,
		DEFAULT_POMODORO_UNTIL_LONG_BREAK_TIME
	)

	private lateinit var countDownTimer: CountDownTimer
	private var timeLeft: Long = 0L
	private var timerType = TimerType.POMODORO_TIMER
	private var autoIncrementPomodoro: Int = 0

	override fun startTimer(timeLength: Long) {
		countDownTimer = object : CountDownTimer(timeLength, COUNT_DOWN_INTERVAL) {
			override fun onTick(leftTimeInMilliseconds: Long) {
				timeLeft = leftTimeInMilliseconds
			}

			override fun onFinish() {
				timerType = if (autoIncrementPomodoro++ == pomodoroTimer.pomodoroUntilLongBreak &&
					timerType == TimerType.POMODORO_TIMER
				) {
					autoIncrementPomodoro = 0
					TimerType.LONG_BREAK_TIMER
				} else {
					when (timerType) {
						TimerType.POMODORO_TIMER -> TimerType.BREAK_TIMER
						TimerType.BREAK_TIMER -> TimerType.POMODORO_TIMER
						TimerType.LONG_BREAK_TIMER -> TimerType.POMODORO_TIMER
					}
				}

				when (timerType) {
					TimerType.POMODORO_TIMER -> startTimer(pomodoroTimer.pomodoroTime)
					TimerType.BREAK_TIMER -> startTimer(pomodoroTimer.breakTime)
					TimerType.LONG_BREAK_TIMER -> startTimer(pomodoroTimer.longBreakTime)
				}
			}
		}.start()
	}

	override fun pauseTimer() {
		countDownTimer.cancel()
	}

	override fun resumeTimer() {
		startTimer(timeLeft)
	}

	override fun stopTimer() {
		countDownTimer.cancel()
		timerType = TimerType.POMODORO_TIMER
		timeLeft = pomodoroTimer.pomodoroTime
	}

	override fun getPomodoroTimer(): PomodoroTimer {
		return pomodoroTimer
	}
}