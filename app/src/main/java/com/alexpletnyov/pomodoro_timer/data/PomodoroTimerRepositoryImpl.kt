package com.alexpletnyov.pomodoro_timer.data

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexpletnyov.pomodoro_timer.domain.PomodoroTimer
import com.alexpletnyov.pomodoro_timer.domain.PomodoroTimerRepository

object PomodoroTimerRepositoryImpl : PomodoroTimerRepository {

	enum class TimerType {
		POMODORO_TIMER, BREAK_TIMER, LONG_BREAK_TIMER
	}

	enum class TimerState {
		STARTED, PAUSED, STOPPED
	}

	private const val COUNT_DOWN_INTERVAL = 1000L
	private const val DEFAULT_POMODORO_TIME = 25_000L
	private const val DEFAULT_BREAK_TIME = 5_000L
	private const val DEFAULT_LONG_BREAK_TIME = 15_000L
	private const val DEFAULT_POMODORO_UNTIL_LONG_BREAK_TIME = 4

	private var pomodoroTimer: PomodoroTimer
	private var pomodoroTimerLD = MutableLiveData<PomodoroTimer>()
	private var timeLeftLD = MutableLiveData<Long>()

	init {
		pomodoroTimer = PomodoroTimer(
			DEFAULT_POMODORO_TIME,
			DEFAULT_BREAK_TIME,
			DEFAULT_LONG_BREAK_TIME,
			DEFAULT_POMODORO_UNTIL_LONG_BREAK_TIME
		)
		updatePomodoroTimer()
	}

	private lateinit var countDownTimer: CountDownTimer
	private var timeLeft: Long = 0L
	private var timerType = TimerType.POMODORO_TIMER
	private var timerState = TimerState.STOPPED
	private var autoIncrementPomodoro: Int = 0

	override fun startTimer(timeLength: Long) {
		if (timerState == TimerState.STOPPED) {
			timerState = TimerState.STARTED
			countDownTimer = object : CountDownTimer(timeLength, COUNT_DOWN_INTERVAL) {
				override fun onTick(leftTimeInMilliseconds: Long) {
					timeLeft = leftTimeInMilliseconds
					//TODO: fix incorrect timeLeft countdown
					updateTimeLeft()
				}

				override fun onFinish() {
					timerState = TimerState.STOPPED
					timerType =
						if (autoIncrementPomodoro++ == pomodoroTimer.pomodoroUntilLongBreak &&
							timerType == TimerType.POMODORO_TIMER
						) {
							autoIncrementPomodoro = 0
							//TODO: fix pomodoroUntilLongBreak logic
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
	}

	override fun pauseTimer() {
		countDownTimer.cancel()
		timerState = TimerState.PAUSED
	}

	override fun resumeTimer() {
		if (timerState == TimerState.PAUSED) {
			timerState = TimerState.STOPPED
			startTimer(timeLeft)
		}
	}

	override fun stopTimer() {
		countDownTimer.cancel()
		timerState = TimerState.STOPPED
		timerType = TimerType.POMODORO_TIMER
		autoIncrementPomodoro = 0
		timeLeft = pomodoroTimer.pomodoroTime
		updateTimeLeft()
	}

	override fun getPomodoroTimer(): LiveData<PomodoroTimer> {
		return pomodoroTimerLD
	}

	override fun editPomodoroTimer(pomodoroTimer: PomodoroTimer) {
		this.pomodoroTimer = pomodoroTimer
		updatePomodoroTimer()
	}

	override fun getTimeLeft(): LiveData<Long> {
		return timeLeftLD
	}

	private fun updatePomodoroTimer() {
		pomodoroTimerLD.value = pomodoroTimer.copy()
	}

	private fun updateTimeLeft() {
		timeLeftLD.value = timeLeft
	}
}
