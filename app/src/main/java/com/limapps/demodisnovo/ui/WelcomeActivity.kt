package com.limapps.demodisnovo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.demodisnovo.R
import com.limapps.demodisnovo.base.BaseActivity
import com.limapps.demodisnovo.source.ResponseApi
import com.limapps.demodisnovo.viewmodel.FormViewModel
import kotlinx.android.synthetic.main.activity_main.*

class WelcomeActivity : BaseFragmentActivity(){

    private lateinit var formViewModel: FormViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formViewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)

        btnPedirJson.setOnClickListener {
            showLoading(true)
            formViewModel.getWidgetsRemoto().observe(this, Observer<ResponseApi> { response ->
                val dataEntity = response.data
                Log.e("responde",response.toString())
                if (response.code.equals("success")){
                    val intent = Intent(this,FormActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    val bundle = Bundle()
                    bundle.putParcelable("response",dataEntity)
                    intent.putExtra("myBundle",bundle)
                    startActivity(intent)
                }else{
                    showLoading(false)
                    Toast.makeText(this,"Algo salio mal, intentalo nuevamente",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
