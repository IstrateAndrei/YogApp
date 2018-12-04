package self.personal.com.yogapp.presentation.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.trial_activity_layout.*
import org.koin.standalone.KoinComponent
import self.personal.com.yogapp.R

class TrialActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trial_activity_layout)
        initListeners()
    }

    @SuppressLint("ResourceAsColor")
    private fun initListeners() {
        start_button.onClick {

            if (start_button.text == resources.getString(R.string.start_string)) {

                //show cancel button
                start_button.text = resources.getString(R.string.cancel_string)
                start_button.setBackgroundColor(android.R.color.black)
                start_button.setTextColor(android.R.color.white)

                showFields(true)

                object : CountDownTimer(10000, 1000) {
                    override fun onFinish() {

                    }

                    override fun onTick(p0: Long) {

                    }

                }.start()
            } else {

                start_button.text = resources.getString(R.string.start_string)
                start_button.setBackgroundColor(android.R.color.white)
                start_button.setTextColor(android.R.color.black)

                showFields(false)
            }

        }
    }

    private fun showFields(isStarted: Boolean) {
        if (isStarted) {
            step_title_txt.visibility = View.VISIBLE
            step_subtitle_txt.visibility = View.VISIBLE
            time_display_txt.visibility = View.VISIBLE
        } else {
            step_title_txt.visibility = View.GONE
            step_subtitle_txt.visibility = View.GONE
            time_display_txt.visibility = View.GONE
        }
    }

}