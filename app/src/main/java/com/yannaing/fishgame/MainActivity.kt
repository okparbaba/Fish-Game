package com.yannaing.fishgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: FlyingFishView
    private val handler = Handler()
    private val Interval = 30L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        gameView = FlyingFishView(this)
        setContentView(gameView)
        val timer = Timer()
        timer.schedule(object :TimerTask(){
            override fun run() {
                handler.post {
                    gameView.invalidate()
                }
            }

        },0,Interval)
    }
}