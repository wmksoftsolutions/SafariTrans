package com.google.mlkit.showstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityShowStatusBinding
import com.google.mlkit.vision.demo.databinding.ActivityUpdateStatusBinding

class UpdateStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_status)
    }
}