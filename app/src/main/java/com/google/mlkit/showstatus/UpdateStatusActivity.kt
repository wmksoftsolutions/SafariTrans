package com.google.mlkit.showstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityShowStatusBinding
import com.google.mlkit.vision.demo.databinding.ActivityUpdateStatusBinding

class UpdateStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateStatusBinding
    lateinit var updateStatusAdapter: UpdateStatusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_status)
        setAdapter()
    }


    private fun setAdapter() {
        binding.recyclerStatus.apply {
            updateStatusAdapter = UpdateStatusAdapter(this@UpdateStatusActivity)
            layoutManager = LinearLayoutManager(this@UpdateStatusActivity)
            adapter = updateStatusAdapter
        }

    }
}