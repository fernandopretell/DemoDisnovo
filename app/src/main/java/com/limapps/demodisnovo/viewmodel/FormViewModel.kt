package com.limapps.demodisnovo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.limapps.demodisnovo.models.ObjectPut
import com.limapps.demodisnovo.repository.FormRepository
import com.limapps.demodisnovo.source.ResponseApi
import com.limapps.demodisnovo.source.ResponseApiPost

/**
 * Created by fernandopretell.
 */
class FormViewModel(): ViewModel() {

    private var formRepository: FormRepository? = null
    private var list_widgets: LiveData<ResponseApi>? = null


    init {

        formRepository = FormRepository()
        list_widgets = formRepository?.listar_widgets()
    }

    //ws
    fun getWidgetsRemoto(): LiveData<ResponseApi> {
        return formRepository?.obtenerWidgets()!!
    }

    fun putWidgetsRemoto(data: ObjectPut): LiveData<ResponseApiPost> {
        return formRepository?.enviarDataWidgets(data)!!
    }
}