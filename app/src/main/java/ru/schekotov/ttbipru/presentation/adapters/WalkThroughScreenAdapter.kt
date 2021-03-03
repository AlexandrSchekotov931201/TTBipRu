package ru.schekotov.ttbipru.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.enums.WalkThroughStateScreen

class WalkThroughScreenAdapter(
    private var walkThroughContent: Array<WalkThroughStateScreen>
) : PagerAdapter() {

    private lateinit var textViewSlider: TextView
    private lateinit var imageViewSlider: ImageView

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val v = LayoutInflater.from(context).inflate(R.layout.walkthrough_item, container, false)
        textViewSlider = v.findViewById(R.id.text_view_item_slider)
        imageViewSlider = v.findViewById(R.id.image_view_item_slider)
        textViewSlider.text = context.getText(walkThroughContent[position].state.headerText)
        imageViewSlider.setImageResource(walkThroughContent[position].state.img)
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as LinearLayout)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun getCount(): Int {
        return walkThroughContent.size
    }
}