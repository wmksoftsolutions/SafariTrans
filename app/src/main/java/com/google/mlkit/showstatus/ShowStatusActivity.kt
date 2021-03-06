package com.google.mlkit.showstatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.home.Data
import com.google.mlkit.utils.Constants
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityShowStatusBinding

class ShowStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowStatusBinding
    lateinit var showStatusAdapter: ShowStatusAdapter
    var list_status = ArrayList<Status>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_status)
        setAdapter()
        intent.extras.let {
            val list: ArrayList<Data>? =
                it!!.getParcelableArrayList<Data>(Constants.TRACK_STATUS_DATA)
            if (list != null)
                createList(list)

            val shipmentID = it.getString(Constants.SHIPMENT_ID)
            binding.status.text = getString(R.string.shipmentID).plus(" $shipmentID")
        }
    }

    private fun createList(list: ArrayList<Data>) {
        if (list.size > 0) {
            val data: Data = list.get(0)
            val s1 = Status("In Warehouse", data.in_warehouse!!)
            val s2 = Status("In Route", data.en_route!!)
            val s3 = Status("Arrived", data.arrived!!)
            val s4 = Status("Pickup", data.pickup!!)
            val s5 = Status("Completed", data.completed!!)
            list_status.clear();
            list_status.add(s1)
            list_status.add(s2)
            list_status.add(s3)
            list_status.add(s4)
            list_status.add(s5)
            showStatusAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        binding.recyclerview.apply {
            showStatusAdapter = ShowStatusAdapter(this@ShowStatusActivity, list_status)
            layoutManager = LinearLayoutManager(this@ShowStatusActivity)
            adapter = showStatusAdapter
        }

    }
}