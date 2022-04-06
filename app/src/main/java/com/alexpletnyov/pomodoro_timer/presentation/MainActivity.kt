package com.alexpletnyov.pomodoro_timer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.alexpletnyov.pomodoro_timer.R

class MainActivity : AppCompatActivity() {

	private lateinit var viewModel: MainViewModel

	private lateinit var timeView: TextView
	private lateinit var buttonStart: Button
	private lateinit var buttonPause: Button
	private lateinit var buttonResume: Button
	private lateinit var buttonStop: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		initViews()
		viewModel.pomodoroTimer.observe(this) {
			val pomodoroTimer = it
			buttonStart.setOnClickListener {
				viewModel.start(pomodoroTimer)
			}
			buttonPause.setOnClickListener {
				viewModel.pause()
			}
			buttonResume.setOnClickListener {
				viewModel.resume()
			}
			buttonStop.setOnClickListener {
				viewModel.stop()
			}
		}
		viewModel.timeLeft.observe(this) {
			timeView.text = viewModel.getTimeLeftText(it)
		}
	}

	private fun initViews() {
		timeView = findViewById(R.id.tv_show_time)
		buttonStart = findViewById(R.id.b_start)
		buttonPause = findViewById(R.id.b_pause)
		buttonResume = findViewById(R.id.b_resume)
		buttonStop = findViewById(R.id.b_stop)
	}
}