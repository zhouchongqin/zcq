package com.example.campuscourierpick_up;
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.*
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使通知栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        Animation()
    }
    private  fun Animation() {
        /*获取屏幕宽高*/
        val dm =  DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(dm)
        val ScreenWidth = dm.widthPixels
        val ScreenHeight = dm.heightPixels
        val  im =  findViewById<ImageView>(R.id.iv_login_bg)
        val animationSet = AnimationSet(true)
        val translateAnimation = TranslateAnimation(
            // X轴的开始位置
            android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
            // X轴的结束位置
            android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
            // Y轴的开始位置
            android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
            // Y轴的结束位置
            android.view.animation.Animation.RELATIVE_TO_SELF, 0.47f)
        translateAnimation.setDuration(7000)
        translateAnimation.setInterpolator(LinearInterpolator())
        translateAnimation.setInterpolator(BounceInterpolator())
        translateAnimation.setRepeatCount(-1) //  设置动画重复次数

        translateAnimation.setRepeatMode(android.view.animation.Animation.REVERSE)
        //translateAnimation.setRepeatMode(Animation.RESTART) //重新从头执行
        //translateAnimation.setRepeatMode(Animation.REVERSE) //反方向执行

        animationSet.addAnimation(translateAnimation)
        im.setAnimation(animationSet)

    }



}

