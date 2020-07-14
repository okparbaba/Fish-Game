package com.yannaing.fishgame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.floor

class FlyingFishView(context:Context) :View(context){
    private var fish = arrayOfNulls<Bitmap>(2)
    private var backgroundImage:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.background)
    private var scorePaint:Paint = Paint()
    private var life = arrayOfNulls<Bitmap>(2)
    private var fishX = 10f
    private var fishY = 0f
    private var fishSpeed = 0f
    private var canvasWidth = 0f
    private var canvasHeight = 0f
    private var touch = false
    private var yellowX = 0f
    private var yellowY = 0f
    private var yellowSpeed = 16f
    private var yellowPaint= Paint()

    private var greenX = 0f
    private var greenY = 0f
    private var greenSpeed = 17f
    private var greenPaint = Paint()

    private var redX = 0f
    private var redY = 0f
    private var redSpeed = 25f
    private var redPaint = Paint()

    private var score = 0
    private var liveCounter = 3
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvasWidth = width.toFloat()
        canvasHeight = height.toFloat()


        canvas?.drawBitmap(backgroundImage,0f,0f,null)

        val minFishY = fish[0]?.height!!.toFloat()
        val maxFishY = canvasHeight - fish[0]!!.height * 3
        fishY += fishSpeed
        if (fishY<minFishY) fishY = minFishY
        if (fishY>maxFishY) fishY = maxFishY
        fishSpeed += 2
        if (touch){
            canvas?.drawBitmap(fish[1]!!,fishX,fishY,null)
            touch = false
        }else canvas?.drawBitmap(fish[0]!!,fishX,fishY,null)


        yellowX -= yellowSpeed
        if (hitBallChecker(yellowX,yellowY)){
            score += 10
            yellowX =- 100f
        }
        if (yellowX<0){
            yellowX = canvasWidth + 21
            yellowY = (floor(Math.random() * (maxFishY-minFishY)) + minFishY).toFloat()
        }
        canvas?.drawCircle(yellowX,yellowY,25f,yellowPaint)

        greenX -= greenSpeed
        if (hitBallChecker(greenX,greenY)){
            score += 20
            greenX =- 100f
        }
        if (greenX<0){
            greenX = canvasWidth + 21
            greenY = (floor(Math.random() * (maxFishY-minFishY)) + minFishY).toFloat()
        }
        canvas?.drawCircle(greenX,greenY,15f,greenPaint)

        redX -= redSpeed
        if (hitBallChecker(redX,redY)){
            redX =- 100f
            liveCounter--;
            if (liveCounter==0){
                Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
                val int = Intent(context,GameOverActivity::class.java)
                int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(int)
            }
        }
        if (redX<0){
            redX = canvasWidth + 21
            redY = (floor(Math.random() * (maxFishY-minFishY)) + minFishY).toFloat()
        }
        canvas?.drawCircle(redX,redY,35f,redPaint)
        canvas?.drawText("Score : $score",20f,70f,scorePaint)
        for (i in 0..2){
            val x = (280+life[0]!!.width * 1.5*i)
            val y = 30
            if (i <liveCounter){
                canvas?.drawBitmap(life[0]!!,x.toFloat(),y.toFloat(),null)
            }else canvas?.drawBitmap(life[1]!!,x.toFloat(),y.toFloat(),null)
        }



    }

    private fun hitBallChecker(x:Float, y:Float):Boolean{
        if (fishX<x && x<(fishX + fish[0]!!.width) && fishY <y && y <(fishY + fish[0]!!.height)){
            return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                touch = true
                fishSpeed = -22f
            }
        }
        return true
    }
    init {
        fish[0] = BitmapFactory.decodeResource(resources,R.drawable.fish1)
        fish[1] = BitmapFactory.decodeResource(resources,R.drawable.fish2)
        scorePaint.color = Color.WHITE
        scorePaint.textSize = 50f

        scorePaint.typeface = Typeface.DEFAULT_BOLD
        scorePaint.isAntiAlias = true

        yellowPaint.color = Color.YELLOW
        yellowPaint.isAntiAlias = false

        greenPaint.color = Color.GREEN
        greenPaint.isAntiAlias = false

        redPaint.color = Color.RED
        redPaint.isAntiAlias = false

        life[0] = BitmapFactory.decodeResource(resources,R.drawable.hearts)
        life[1] = BitmapFactory.decodeResource(resources,R.drawable.heart_grey)
        fishY = 550f
    }
}