package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.adapters.WalkThroughScreenAdapter
import ru.schekotov.ttbipru.constans.SharedPreferencesConst

class WalkThroughActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var walkThroughScreenAdapter: WalkThroughScreenAdapter
    private lateinit var linearLayoutDots: LinearLayout
    private lateinit var dots: Array<TextView?>
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walkthrough_activity)

        walkThroughScreenAdapter = WalkThroughScreenAdapter(this)

        initView()
        renderDotsIndicator()
        initListener()
    }

    /** Инициализация View */
    private fun initView() {
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = walkThroughScreenAdapter
        linearLayoutDots = findViewById(R.id.view_pager_dots)
        nextButton = Button(this)
    }

    /** Инициализация Listener */
    private fun initListener() {
        nextButton.setOnClickListener {
            getSharedPreferences(SharedPreferencesConst.APP_PREFERENCES, Context.MODE_PRIVATE).edit()
                .putBoolean(SharedPreferencesConst.IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY, true)
                .apply()
            startActivity(MainActivity.newIntent(this))
            finish()
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //Нет необходимости для реализации
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Нет необходимости для реализации
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPageSelected(position: Int) {
                if (walkThroughScreenAdapter.count - 1 == position) {
                    renderNextButton()
                } else {
                    renderDotsIndicator(position)
                }
            }
        })
    }

    /**
     * Отрисовать индикатор выбранной страницы с дефолтным значением
     */
    private fun renderDotsIndicator() {
        renderDotsIndicator(0)
    }

    /**
     * Отрисовать индикатор выбранной страницы с указанием позиции выбранной страницы
     *
     * @param selectedPosition позция выбранной страницы
     */
    private fun renderDotsIndicator(selectedPosition: Int) {
        dots = arrayOfNulls(walkThroughScreenAdapter.count)
        linearLayoutDots.removeAllViews()
        for (index in dots.indices) {
            dots[index] = TextView(this)
            dots[index]?.apply {
                text = "•"
                textSize = 45F
                setTextColor(resources.getColor(R.color.black_alpha_4))
            }
            linearLayoutDots.addView(dots[index])
        }
        if (dots.isNotEmpty()) {
            dots[selectedPosition]?.setTextColor(resources.getColor(R.color.black_alpha_1))
        }
    }

    /**
     * Отрисовать индикатор выбранной страницы с указанием позиции выбранной страницы
     *
     * @param selectedPosition позция выбранной страницы
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun renderNextButton() {
        linearLayoutDots.removeAllViews()
        nextButton.apply {
            text = "Узнать штрафы"
            backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.orange)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        linearLayoutDots.addView(nextButton)
    }

    companion object {
        const val TAG = "WalkThroughActivity"

        fun newIntent(context: Context): Intent {
            return Intent(context, WalkThroughActivity::class.java)
        }
    }
}