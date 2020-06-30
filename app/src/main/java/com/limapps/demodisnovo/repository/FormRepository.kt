package com.limapps.demodisnovo.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.limapps.demodisnovo.models.ObjectPut
import com.limapps.demodisnovo.source.HelperWs
import com.limapps.demodisnovo.source.ResponseApi
import com.limapps.demodisnovo.source.ResponseApiPost
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class FormRepository() {

    //private var responseDAo: ResponseDao? = null
    var list_peliculas: LiveData<ResponseApi>? = null

    //Ws
    private var response: MutableLiveData<ResponseApi>? = null
    private var responsePost: MutableLiveData<ResponseApiPost>? = null
    internal var webserviceData = HelperWs.getServiceData()

    fun listar_widgets(): LiveData<ResponseApi>?{
        return list_peliculas
    }

    //ws
    fun obtenerWidgets(): LiveData<ResponseApi> {

        if (response == null) {
            response = MutableLiveData()
            listarWidgetsRemoto()
        }

        return response as MutableLiveData<ResponseApi>
    }

    fun enviarDataWidgets(data: ObjectPut): LiveData<ResponseApiPost> {

        if (responsePost == null) {
            responsePost = MutableLiveData()
            enviarValoresWidgets(data)
        }

        return responsePost as MutableLiveData<ResponseApiPost>
    }

    private fun listarWidgetsRemoto() {

        webserviceData.obtenerDataRemota().enqueue(object : Callback<ResponseApi> {
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseApi>, respon: Response<ResponseApi>) {
                when(respon.code()){

                    201 -> {
                        //deleteAll()
                        respon.body()?.let {
                             response?.value = it
                        }
                    }
                }
            }
        })
    }

    private fun enviarValoresWidgets(data: ObjectPut) {

        webserviceData.enviarDataRemota(data).enqueue(object : Callback<ResponseApiPost> {
            override fun onFailure(call: Call<ResponseApiPost>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseApiPost>, respon: Response<ResponseApiPost>) {
                when(respon.code()){

                    200 -> {
                        //deleteAll()
                        respon.body()?.let {
                            responsePost?.value = it
                        }
                    }
                    else -> {
                        responsePost?.value = ResponseApiPost(code = respon.code().toString(), message = respon.message())
                    }
                }
            }
        })
    }

    /*fun transformApiToEntity(response: ResponseApi):ResponseEntity{

        return ResponseEntity(response.id,response.page,response.revenue,response.name,response.description,response.backdrop_path,response.results,response.average_rating,response.poster_path,"","")
    }*/
}