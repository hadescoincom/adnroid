package com.hds.hdswallet.screens.app_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.hds.hdswallet.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, AppActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        androidx.multidex.MultiDex.install(this);
    }

}