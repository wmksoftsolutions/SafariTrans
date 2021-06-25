package com.google.mlkit.showstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityShowStatusBinding

class ShowStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowStatusBinding
    lateinit var showStatusAdapter: ShowStatusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_status)
        setAdapter()
    }

    private fun setAdapter() {
        showStatusAdapter = ShowStatusAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = showStatusAdapter
    }
}