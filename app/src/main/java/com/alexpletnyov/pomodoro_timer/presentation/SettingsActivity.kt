package com.alexpletnyov.pomodoro_timer.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.alexpletnyov.pomodoro_timer.R
import com.google.android.material.textfield.TextInputLayout

class SettingsActivity : AppCompatActivity() {

	private lateinit var tilPomodoroTime: TextInputLayout
	private lateinit var tilBreakTime: TextInputLayout
	private lateinit var tilLongBreakTime: TextInputLayout
	private lateinit var tilPomodoroUntilLongBreak: TextInputLayout
	private lateinit var etPomodoroTime: EditText
	private lateinit var etBreakTime: EditText
	private lateinit var etLongBreakTime: EditText
	private lateinit var etPomodoroUntilLongBreak: EditText
	private lateinit var buttonSave: Button

	private lateinit var pomodoroTime: String
	private lateinit var breakTime: String
	private lateinit var longBreakTime: String
	private lateinit var pomodoroUntilLongBreak: String

	private lateinit var viewModel: SettingsViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)
		viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
		initViews()
		parseTime()

		viewModel.getPomodoroTimer()
		viewModel.pomodoroTimer.observe(this) {

			etPomodoroTime.setText((it.pomodoroTime / SettingsViewModel.MINUTE_TO_MILLISECOND).toString())
			etBreakTime.setText(it.breakTime.toString())
			etLongBreakTime.setText(it.longBreakTime.toString())
			etPomodoroUntilLongBreak.setText(it.pomodoroUntilLongBreak.toString())
		}
	}

	private fun parseTime() {

	}

	private fun initViews() {
		tilPomodoroTime = findViewById(R.id.til_pomodoro_time)
		tilBreakTime = findViewById(R.id.til_break_time)
		tilLongBreakTime = findViewById(R.id.til_long_break_time)
		tilPomodoroUntilLongBreak = findViewById(R.id.til_pomodoro_until_long_break)
		etPomodoroTime = findViewById(R.id.et_pomodoro_time)
		etBreakTime = findViewById(R.id.et_break_time)
		etLongBreakTime = findViewById(R.id.et_long_break_time)
		etPomodoroUntilLongBreak = findViewById(R.id.et_pomodoro_until_long_break)
		buttonSave = findViewById(R.id.save_button)
	}

	companion object {
		fun newIntentOpenSettings(context: Context): Intent {
			return Intent(context, SettingsActivity::class.java)
		}
	}
}