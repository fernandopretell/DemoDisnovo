package com.limapps.base.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.limapps.base.R
import com.limapps.baseui.others.OnClickViewListener
import com.limapps.base.utils.Utils
import dagger.android.support.DaggerAppCompatActivity

class UpdateAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_app_layout)
        findViewById<View>(R.id.update_now).setOnClickListener(object : OnClickViewListener() {
            override fun onClickView(view: View) {
                Utils.openPlayStoreOnApp(this@UpdateAppActivity)
                finish()
            }
        })
    }

    override fun onBackPressed() {}
}
