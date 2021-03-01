package ru.schekotov.ttbipru.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.model.WalkThroughContentModel

class WalkThroughScreenAdapter(val context: Context) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var textViewSlider: TextView
    //TODO вынести создание массива за пределы адаптера
    private val walkThroughContent: Array<WalkThroughContentModel> = arrayOf(
        WalkThroughContentModel(R.string.pay_penalties_with_50_discount),
        WalkThroughContentModel(R.string.get_notified_of_new_fines),
        WalkThroughContentModel(R.string.repayment_guarantee_receipt_as_in_the_bank),
        WalkThroughContentModel(R.string.obey_traffic_rules)
    )

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.walkthrough_item, container, false)
        textViewSlider = v.findViewById(R.id.text_item_slider)
        textViewSlider.text = context.getText(walkThroughContent[position].headerText)
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