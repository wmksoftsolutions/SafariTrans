package com.google.mlkit.showstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.home.Data
import com.google.mlkit.utils.Constants
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
        intent.extras.let {
            val list: ArrayList<Data>? =
                it!!.getParcelableArrayList<Data>(Constants.TRACK_STATUS_DATA)
            if (list != null)
                createList(list)
        }
    }

    private fun createList(list: java.util.ArrayList<Data>) {

    }


    private fun setAdapter() {
        binding.recyclerStatus.apply {
            updateStatusAdapter = UpdateStatusAdapter(this@UpdateStatusActivity)
            layoutManager = LinearLayoutManager(this@UpdateStatusActivity)
            adapter = updateStatusAdapter
        }

    }
}