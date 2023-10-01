package com.example.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.SeekBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var seekBarCelsius: SeekBar
    private lateinit var seekBarFahrenheit: SeekBar
    private lateinit var textViewCelsius: TextView
    private lateinit var textViewFahrenheit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarCelsius = findViewById(R.id.seekBarCelsius)
        seekBarFahrenheit = findViewById(R.id.seekBarFahrenheit)
        textViewCelsius = findViewById(R.id.textViewCelsius)
        textViewFahrenheit = findViewById(R.id.textViewFahrenheit)

        updateTemperature(0, 32)

        val seekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar) {
                    seekBarCelsius -> {
                        val fahrenheit = celsiusToFahrenheit(progress)
                        updateTemperature(progress, fahrenheit)
                        seekBarFahrenheit.progress = fahrenheit
                        showTemperatureSnackbar(progress)
                    }
                    seekBarFahrenheit -> {
                        val celsius = fahrenheitToCelsius(progress)
                        if (celsius < 0) {
                            seekBarFahrenheit.progress = 0
                        } else {
                            updateTemperature(celsius, progress)
                            seekBarCelsius.progress = celsius
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }

        seekBarCelsius.setOnSeekBarChangeListener(seekBarListener)
        seekBarFahrenheit.setOnSeekBarChangeListener(seekBarListener)

    }

    private fun celsiusToFahrenheit(celsius: Int): Int {
        return ((celsius * 9)/5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return ((fahrenheit - 32) * 5) / 9
    }

    private fun updateTemperature(celsius: Int, fahrenheit: Int) {
        textViewCelsius.text = "Celsius: $celsius°C"
        textViewFahrenheit.text = "Fahrenheit: $fahrenheit°F"
    }

    private fun showTemperatureSnackbar(celsius: Int) {
        val message = if (celsius <= 20) {
            "I wish it were warmer."
        } else {
            "I wish it were colder."
        }

        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT)
            .show()
    }

}