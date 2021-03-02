package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst
import ru.schekotov.ttbipru.presentation.adapters.WalkThroughScreenAdapter
import ru.schekotov.ttbipru.presentation.viewModel.WalkThroughViewModel

/**
 * Активити WalkThrough экранов
 *
 * @author Щёкотов Александр
 */
class WalkThroughActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var walkThroughScreenAdapter: WalkThroughScreenAdapter
    private lateinit var linearLayoutDots: LinearLayout
    private lateinit var dots: Array<TextView?>
    private lateinit var nextButton: Button

    private lateinit var walkThroughViewModel: WalkThroughViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walkthrough_activity)
        walkThroughScreenAdapter = WalkThroughScreenAdapter(this)
        initViewModel()
        initView()
        initListener()
        registerObservers()
    }

    /** Инициализация ViewModel */
    private fun initViewModel() {
        walkThroughViewModel = ViewModelProvider(this).get(WalkThroughViewModel::class.java)
    }

    /** Инициализация View */
    private fun initView() {
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = walkThroughScreenAdapter
        linearLayoutDots = findViewById(R.id.view_pager_dots)
        nextButton = findViewById(R.id.view_pager_next_button)
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
                    walkThroughViewModel.onShowNextButton()
                } else {
                    walkThroughViewModel.onHideNextButton()
                    walkThroughViewModel.onShowDotsIndicator(position)
                }
            }
        })
    }

    /**
     * Отрисовать индикатор выбранной страницы с указанием позиции выбранной страницы
     *
     * @param selectedPosition позция выбранной страницы
     */
    private fun showDotsIndicator(selectedPosition: Int) {
        dots = arrayOfNulls(walkThroughScreenAdapter.count)
        linearLayoutDots.removeAllViews()
        for (index in dots.indices) {
            dots[index] = TextView(this)
            dots[index]?.apply {
                text = "•"
                textSize = 45F
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    setTextColor(resources.getColor(R.color.black_alpha_4, context.theme))
                else
                    setTextColor(resources.getColor(R.color.black_alpha_4))
            }
            linearLayoutDots.addView(dots[index])
        }
        if (dots.isNotEmpty()) {
            dots[selectedPosition]?.setTextColor(resources.getColor(R.color.black_alpha_1))
        }
    }

    /** Показать кнопку "Узнать штрафы" */
    private fun showNextButton(isShow: Boolean) {
        if (isShow) {
            nextButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.GONE
        }
    }

    /** регистрация подписчиков */
    private fun registerObservers() {
        walkThroughViewModel.getShowNextButtonLiveDate().observe(this, this::showNextButton)
        walkThroughViewModel.getShowDotsIndicatorLiveDate().observe(this, this::showDotsIndicator)
    }

    companion object {
        const val TAG = "WalkThroughActivity"

        /** создание интента активити с необходимыми параметрами */
        fun newIntent(context: Context): Intent {
            return Intent(context, WalkThroughActivity::class.java)
        }
    }
}