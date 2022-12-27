package com.example.campuscourierpick_up

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.content.Intent


/**
 * 作者：周重庆
 * 开屏页
 *
 */
class SplashActivity :  Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(this, R.layout.activity_splash, null)
        setContentView(view)
    }
    override fun onStart() {
        super.onStart()
        Thread {
            val start = System.currentTimeMillis()
            val costTime = System.currentTimeMillis() - start
            //等待sleeptime时长
            if (sleepTime - costTime > 0) {
                try {
                    Thread.sleep(sleepTime - costTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            //进入主页面
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }.start()
    }

    companion object {
        private const val sleepTime = 2000
    }
}