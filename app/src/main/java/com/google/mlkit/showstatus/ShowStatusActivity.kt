package com.google.mlkit.showstatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.home.Data
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.utils.Constants
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityShowStatusBinding

class ShowStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowStatusBinding
    lateinit var showStatusAdapter: ShowStatusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_status)
        setAdapter()

        intent.extras.let {
          val list=  it!!.getParcelableArrayList<ArrayList<Data>>(Constants.TRACK_STATUS_DATA)
        }
    }

    private fun setAdapter() {
        binding.recyclerview.apply {
            showStatusAdapter = ShowStatusAdapter(this@ShowStatusActivity)
            layoutManager = LinearLayoutManager(this@ShowStatusActivity)
            adapter = showStatusAdapter
        }

    }
}