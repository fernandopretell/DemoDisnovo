package com.limapps.demodisnovo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.limapps.common.isNotNullOrEmpty
import com.limapps.demodisnovo.R
import com.limapps.demodisnovo.models.*
import java.text.SimpleDateFormat
import java.util.*

class DataAdapter(private val context: Context) :
    RecyclerView.Adapter<DataAdapter.BaseViewHolder<*>>() {
    private var adapterDataList: ArrayList<Any> = arrayListOf()

    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_TEXT_MULTI_LINE = 1
        private const val TYPE_CALENDAR = 2
        private const val TYPE_SLIDER = 3
        private const val TYPE_NUMERIC = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_TEXT -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_input_text_single_line, parent, false)
                TextSingleLineViewHolder(view)
            }
            TYPE_TEXT_MULTI_LINE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_input_text_multiline, parent, false)
                TextMultiLineViewHolder(view)
            }
            TYPE_CALENDAR -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_calendar_view, parent, false)
                CalendarViewHolder(view)
            }

            TYPE_SLIDER -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_slider, parent, false)
                SliderViewHolder(view)
            }

            TYPE_NUMERIC -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_input_text_numeric, parent, false)
                NumericViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = adapterDataList[position]
        return when (comparable) {
            is TextSingleLineDataModel -> TYPE_TEXT
            is TextMultiLineDataModel -> TYPE_TEXT_MULTI_LINE
            is CalendarDataModel -> TYPE_CALENDAR
            is SliderDataModel -> TYPE_SLIDER
            is NumericDataModel -> TYPE_NUMERIC
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        //holder.setIsRecyclable(false)
        val element = adapterDataList[position]
        when (holder) {
            is TextSingleLineViewHolder -> holder.bind(element as TextSingleLineDataModel)
            is TextMultiLineViewHolder -> holder.bind(element as TextMultiLineDataModel)
            is CalendarViewHolder -> holder.bind(element as CalendarDataModel)
            is SliderViewHolder -> holder.bind(element as SliderDataModel)
            is NumericViewHolder -> holder.bind(element as NumericDataModel)
            else -> throw IllegalArgumentException()
        }
    }

    fun updateData(newData: List<Any>) {
        adapterDataList.clear()
        adapterDataList.addAll(newData)
        notifyDataSetChanged()
    }


    inner class TextSingleLineViewHolder(itemView: View) : BaseViewHolder<TextSingleLineDataModel>(itemView) {

        override fun bind(item: TextSingleLineDataModel) {
            val etSingle = itemView.findViewById<AppCompatEditText>(R.id.etSingleLine)
            etSingle.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    item.value = p0.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }

    inner class TextMultiLineViewHolder(itemView: View) : BaseViewHolder<TextMultiLineDataModel>(itemView) {

        override fun bind(item: TextMultiLineDataModel) {
            val etMultiline = itemView.findViewById<AppCompatEditText>(R.id.etMultiLine)
            etMultiline.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    item.value = p0.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }

    inner class CalendarViewHolder(itemView: View) : BaseViewHolder<CalendarDataModel>(itemView) {

        @SuppressLint("SimpleDateFormat")
        override fun bind(item: CalendarDataModel) {

            val formatter1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

            val calendar = itemView.findViewById<CalendarView>(R.id.calendarView)
            item.value = formatter1.format(calendar.date)
            if(!item.value.equals("") && item.value.isNotNullOrEmpty()){
                //val formatter1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                val date1: Long = formatter1.parse(item.value!!)?.time!!
                 calendar.date = date1
            }

            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->

                val date = GregorianCalendar(year, month - 1, dayOfMonth).time
                item.value = formatter1.format(date)

            }
        }
    }

    inner class SliderViewHolder(itemView: View) : BaseViewHolder<SliderDataModel>(itemView) {

        override fun bind(item: SliderDataModel) {
            val slider = itemView.findViewById<androidx.appcompat.widget.AppCompatSeekBar>(R.id.seekbar)
            val tvRating = itemView.findViewById<TextView>(R.id.textView2)

            slider.progress = item.min
            tvRating.text = slider.progress.toString()

            item.value = item.min.toString()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                slider.min = item.min
                slider.max = item.max
            }else{
                slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int,fromUser: Boolean) {
                        val min = item.min
                        val max = item.max


                        if(progress<min){
                            slider.setProgress(min)
                        }

                        if(progress>max){
                            slider.setProgress(max)
                        }

                        tvRating.text = slider.progress.toString()
                        item.value = slider.progress.toString()

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }

                })
            }
        }
    }

    inner class NumericViewHolder(itemView: View) : BaseViewHolder<NumericDataModel>(itemView) {

        override fun bind(item: NumericDataModel) {
            val etNumeric = itemView.findViewById<AppCompatEditText>(R.id.etNumeric)
            etNumeric.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    item.value = p0.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }


}