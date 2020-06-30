package com.limapps.demodisnovo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.utils.ToastUtil
import com.limapps.demodisnovo.R
import com.limapps.demodisnovo.adapters.DataAdapter
import com.limapps.demodisnovo.models.*
import com.limapps.demodisnovo.source.Data
import com.limapps.demodisnovo.source.ResponseApiPost
import com.limapps.demodisnovo.viewmodel.FormViewModel
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : BaseFragmentActivity() {

    private lateinit var adapter: DataAdapter
    private lateinit var formViewModel: FormViewModel
    private lateinit var lim: LinearLayoutManager
    private var list: ArrayList<Any>? = null

    private var idForm = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        formViewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)


        val bundle = intent.getBundleExtra("myBundle")
        var response = bundle.getParcelable<Data>("response") as Data
        Log.e("data", response.elements?.size.toString())

        idForm = response.id
        adapter = DataAdapter(this)
        lim = LinearLayoutManager(this)
        lim.orientation = RecyclerView.VERTICAL
        rvForm!!.layoutManager = lim
        rvForm!!.adapter = adapter

        /*rvForm!!.getRecycledViewPool().setMaxRecycledViews(0, 0)
        rvForm!!.getRecycledViewPool().setMaxRecycledViews(1, 0)
        rvForm!!.getRecycledViewPool().setMaxRecycledViews(2, 0)
        rvForm!!.getRecycledViewPool().setMaxRecycledViews(3, 0)
        rvForm!!.getRecycledViewPool().setMaxRecycledViews(4, 0)*/

        list = arrayListOf<Any>()

        response.elements?.forEach {
            when (it.component_type) {
                "slider" -> list!!.add(
                    SliderDataModel(
                        it.component_type,
                        it.field_name_key,
                        it.field_name_view,
                        it.ordinal,
                        it.min,
                        it.max
                    )
                )
                "numeric" -> list!!.add(NumericDataModel())
                "text_multiline" -> list!!.add(TextMultiLineDataModel())
                "text" -> list!!.add(TextSingleLineDataModel())
                "calendar" -> list!!.add(CalendarDataModel(value = it.value))
            }
        }


        adapter.updateData(list!!)

        val gson = GsonBuilder().setPrettyPrinting().create()
        tvJson.text = gson.toJson(response.elements)
        tvJson.setMovementMethod(ScrollingMovementMethod())

        btnEnviarData.setOnClickListener {

            showLoading(true)

            val listFillFieds = comprobeInputsFields().size

            if (listFillFieds == list!!.size) {
                //val data: ObjectPut = getDataInputForms()

                val data = ObjectPut(comprobeInputsFields(), idForm)

                Log.e("TAG", Gson().toJson(data))

                tvJson.text = "JSON ENVIADO \n"+gson.toJson(data)
                tvJson.setTextColor(resources.getColor(android.R.color.holo_red_dark))


                formViewModel.putWidgetsRemoto(data)
                    .observe(this, Observer<ResponseApiPost> { response ->
                        Log.e("estado",response.message )
                        if (response.code.equals("success")) {
                            //Toast.makeText(this,"Data enviada correctamente")
                            ToastUtil.showSuccessfulAlert(this, "Data enviada correctamente")
                            showLoading(false)
                            finish()
                        } else {
                            ToastUtil.showErrorAlert(this, response.message)
                            showLoading(false)
                        }

                    })
            } else {
                ToastUtil.showErrorAlert(this, "Debes llenar todos los campos")
                showLoading(false)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun comprobeInputsFields(): List<WigdetProperty> {

        var numFillFields = 0

        val listaDataPut = arrayListOf<WigdetProperty>()

        list?.forEachIndexed { index, any ->
            when (any) {
                is TextSingleLineDataModel -> {
                    val widgetp = WigdetProperty("singleLineText", any.value)
                    if(any.value.length>0){
                        listaDataPut.add(widgetp)
                        numFillFields++
                    }

                }

                is TextMultiLineDataModel -> {
                    val widgetp = WigdetProperty("multiLineText", any.value)
                    if(any.value.length>0){
                        listaDataPut.add(widgetp)
                        numFillFields++
                    }
                }

                is CalendarDataModel -> {
                    val widgetp = WigdetProperty("calendar", any.value!!)
                    if(any.value!!.length>0){
                        listaDataPut.add(widgetp)
                        numFillFields++
                    }
                }

                is SliderDataModel -> {
                    val widgetp = WigdetProperty("slider", any.value)
                    if(any.value.length>0){
                        listaDataPut.add(widgetp)
                        numFillFields++
                    }
                }

                is NumericDataModel -> {
                    val widgetp = WigdetProperty("textNumeric", any.value)
                    if(any.value.length>0){
                        listaDataPut.add(widgetp)
                        numFillFields++
                    }
                }

            }
        }

        /*list?.forEachIndexed { index, any ->
            when (any) {
                is TextSingleLineDataModel -> {
                    val textViewSingleView = rvForm.layoutManager?.findViewByPosition(index)
                    val textViewSingle =
                        textViewSingleView?.findViewById<AppCompatEditText>(R.id.etSingleLine)
                    try {
                        if (textViewSingle?.text?.length!! > 0) {
                            numFillFields++
                            val widgetp =
                                WigdetProperty("singleText", textViewSingle.text.toString())
                            listaDataPut.add(widgetp)
                        }
                    } catch (e: Exception) {
                        ToastUtil.showErrorAlert(this@FormActivity, "vuelve a intentarlo")
                    }
                }

                is TextMultiLineDataModel -> {
                    val textViewMultiLineView = rvForm.layoutManager?.findViewByPosition(index)
                    val textViewMultiLine =
                        textViewMultiLineView?.findViewById<AppCompatEditText>(R.id.etMultiLine)
                    try {
                        if (textViewMultiLine?.text?.length!! > 0) {
                            numFillFields++
                            val widgetp =
                                WigdetProperty("multiLineText", textViewMultiLine.text.toString())
                            listaDataPut.add(widgetp)
                        }
                    } catch (e: Exception) {
                        ToastUtil.showErrorAlert(this@FormActivity, "vuelve a intentarlo")
                    }
                }

                is CalendarDataModel -> {
                    val calendarViewView = rvForm.layoutManager?.findViewByPosition(index)
                    val calendarView =
                        calendarViewView?.findViewById<CalendarView>(R.id.calendarView)
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    try {
                        val selectedDate: String = sdf.format(Date(calendarView?.getDate()!!))
                        val widgetp = WigdetProperty("calendar", selectedDate)
                        listaDataPut.add(widgetp)
                        numFillFields++
                    } catch (e: Exception) {
                        ToastUtil.showErrorAlert(this@FormActivity, "vuelve a intentarlo")
                    }

                }

                is SliderDataModel -> {
                    val sliderContainerView = rvForm.layoutManager?.findViewByPosition(index)
                    val tvValue = sliderContainerView?.findViewById<TextView>(R.id.textView2)
                    try {
                        val widgetp = WigdetProperty("slider", tvValue?.text.toString())
                        if (tvValue?.text?.length!! > 0) {
                            listaDataPut.add(widgetp)
                            numFillFields++
                        }
                    } catch (e: Exception) {
                        ToastUtil.showErrorAlert(this@FormActivity, "vuelve a intentarlo")
                    }

                }

                is NumericDataModel -> {
                    val numericTextView = rvForm.layoutManager?.findViewByPosition(index)
                    val numericText =
                        numericTextView?.findViewById<AppCompatEditText>(R.id.etNumeric)
                    try {
                        if (numericText?.text?.length!! > 0) {
                            val widgetp = WigdetProperty("numericText", numericText.text.toString())
                            listaDataPut.add(widgetp)
                            numFillFields++
                        }
                    }catch (e: Exception) {
                        ToastUtil.showErrorAlert(this@FormActivity, "vuelve a intentarlo")
                    }
                }

            }
        }*/

        //showMessage("campos llenos")
        return listaDataPut


    }

    /*private fun getDataInputForms(): ObjectPut {


      return
     }*/

    override fun onBackPressed() {
        startActivity(Intent(this, WelcomeActivity::class.java))
    }
}
