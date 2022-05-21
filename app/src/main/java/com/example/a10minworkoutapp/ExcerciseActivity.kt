package com.example.a10minworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaParser
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a10minworkoutapp.databinding.ActivityExcerciseBinding
import com.example.a10minworkoutapp.databinding.DialogCustomBackBinding
import java.lang.Exception
import java.net.URI
import java.util.*
import java.util.logging.Level.parse
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var bindings: ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var restTimeDuration: Long = 10
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress: Int =  0
    private var exerciseTimeDuration: Long = 30

    private var exercise: ArrayList<ExerciseModel>? = null
    private var currentExercisePos: Int = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        bindings = ActivityExcerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindings?.root)

        exercise = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)

        setSupportActionBar(bindings?.toolbarExcercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        bindings?.toolbarExcercise?.setNavigationOnClickListener {
            customDialogFunction()
        }

        setRestView()
        setupExerciseStatusRecyclerView()

    }

    private fun setRestProgressBar(){
        bindings?.progressbarrest?.progress = restProgress
        restTimer = object : CountDownTimer(restTimeDuration*1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                bindings?.progressbarrest?.progress = 10 - restProgress
                bindings?.tvTimer?.text = (10-restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePos++
                exercise!![currentExercisePos].setisSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setRestView() {
        try{
            val soundURI = Uri.parse("android.resource://com.example.a10minworkoutapp/"+R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
        bindings?.flrestview?.visibility = View.VISIBLE
        bindings?.tvTitle?.visibility = View.VISIBLE
        bindings?.tvExerciseName?.visibility = View.INVISIBLE
        bindings?.flexerciseview?.visibility = View.INVISIBLE
        bindings?.ivImage?.visibility = View.INVISIBLE
        bindings?.tvupcominglabel?.visibility = View.VISIBLE
        bindings?.tvupcomingexercisename?.visibility = View.VISIBLE
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        bindings?.tvupcomingexercisename?.text = exercise!![currentExercisePos+1].getName()
        speakOut("Upcoming Exercise is ${bindings?.tvupcomingexercisename?.text.toString()}")
        setRestProgressBar()
    }

    private fun setupExerciseView(){
    bindings?.flrestview?.visibility = View.INVISIBLE
        bindings?.tvTitle?.visibility = View.INVISIBLE
        bindings?.tvExerciseName?.visibility = View.VISIBLE
        bindings?.flexerciseview?.visibility = View.VISIBLE
        bindings?.ivImage?.visibility = View.VISIBLE
        bindings?.tvupcominglabel?.visibility = View.INVISIBLE
        bindings?.tvupcomingexercisename?.visibility = View.INVISIBLE
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        bindings?.ivImage?.setImageResource(exercise!![currentExercisePos].getImage())
        bindings?.tvExerciseName?.text = exercise!![currentExercisePos].getName()
        speakOut("Start")
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        bindings?.progressbarexercise?.progress = restProgress
        exerciseTimer = object : CountDownTimer(exerciseTimeDuration*1000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                bindings?.progressbarexercise?.progress = 30 - exerciseProgress
                bindings?.tvTimerExercise?.text = (30-exerciseProgress).toString()
            }
            override fun onFinish() {
                exercise!![currentExercisePos].setisSelected(false)
                exercise!![currentExercisePos].setisCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                if(currentExercisePos<exercise?.size!!-1) {
                    setRestView()
                } else {
                    finish()
                    val intent = Intent(applicationContext, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language Specified is Not Supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun setupExerciseStatusRecyclerView() {
        bindings?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exercise!!)
        bindings?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun customDialogFunction(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@ExcerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogFunction()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null) {
            player?.stop()
        }
        bindings=null
    }

}