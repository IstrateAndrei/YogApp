package self.personal.com.yogapp.presentation.activities

import android.Manifest
import android.annotation.SuppressLint
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mcxiaoke.koi.ext.onClick
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.trial_activity_layout.*
import org.koin.standalone.KoinComponent
import self.personal.com.yogapp.R
import java.lang.Exception

class TrialActivity : AppCompatActivity(), KoinComponent {

    lateinit var mPlayer: MediaPlayer

    private val mRecorder = MediaRecorder()
    private var mEMA = 0.0
    var recordHandler = Handler()

    private val recordRunnable = Runnable { doJob() }

    private fun doJob() {
        Log.e("SoundAmp", "${getAmpEMA()}")
        recordHandler.postDelayed(recordRunnable, 500)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trial_activity_layout)
        mPlayer = MediaPlayer.create(this, R.raw.result)
        initListeners()
        RxPermissions(this).request(Manifest.permission.RECORD_AUDIO)
                .subscribe { granted ->
                    if (granted) {

                    } else
                        Toast.makeText(this, "We need access to your microphone for this", Toast.LENGTH_SHORT).show()
                }
    }

    fun getAmpEMA(): Double{
        mEMA = 0.6 * getRecordingAmplitude() + (1.0 - 0.6) * mEMA
        return mEMA
    }
    fun startRecorder() {
        try {
            mRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile("/dev/null")
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mRecorder.let {
            mRecorder.stop()
            mRecorder.release()
        }
    }

    private fun getRecordingAmplitude(): Int {
        return mRecorder.maxAmplitude
    }

    private val countTimer = object : CountDownTimer(10000, 1000) {
        override fun onTick(p0: Long) {
            displayTime(p0)
        }

        override fun onFinish() {
            //todo proceed to next step
            mPlayer.start()
            Handler().postDelayed({
                startRecorder()
                recordHandler.post(recordRunnable)
            }, 5000)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun initListeners() {
        start_button.onClick {

            if (start_button.text == resources.getString(R.string.start_string)) {

                //show cancel button
                start_button.text = resources.getString(R.string.cancel_string)
                start_button.background = resources.getDrawable(R.drawable.cancel_button_drawable, null)
                showFields(true)
                countTimer.start()
            } else {

                start_button.text = resources.getString(R.string.start_string)
                start_button.background = resources.getDrawable(R.drawable.start_button_drawable, null)
                showFields(false)
                countTimer.cancel()
            }
        }
    }

    private fun displayTime(time: Long) {

        val seconds = (time / 1000) % 60
        val minutes = (time / 1000) / 60
        val displayText: String
        displayText = if (seconds < 10)
            "$minutes:0$seconds"
        else
            "$minutes:$seconds"

        time_display_txt.text = displayText
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

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecording()
        mPlayer.release()
    }
}