package com.google.mlkit.showstatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.home.Data
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Constants
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityUpdateStatusBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateStatusActivity : AppCompatActivity(), UpdateStatusInterface {
    private var shipment_id: String = ""
    lateinit var binding: ActivityUpdateStatusBinding
    var list_status = ArrayList<Status>()
    private lateinit var updateStatusAdapter: UpdateStatusAdapter
    private val updateStatusViewModel: UpdateStatusViewModel by viewModel<UpdateStatusViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_status)
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

    private fun updateStatusResult() {
        if (CommonMethods.isNetworkAvailable(this)) {
            val updateStatusRequest = UpdateStatusRequest(
                shipment_id,
                list_status.get(0).status,
                list_status.get(1).status,
                list_status.get(2).status,
                list_status.get(3).status,
                list_status.get(4).status
            )
            updateStatusViewModel.updateStatus(updateStatusRequest)
            if (!updateStatusViewModel.statusResponse.hasObservers()) {
                updateStatusViewModel.statusResponse.observe(this, {
                    when (it.status) {
                        ResultStatus.LOADING.ordinal -> {
                            CommonMethods.showLoader(this)
                        }
                        ResultStatus.ERROR.ordinal -> {
                            CommonMethods.hideLoader()
                            CommonMethods.showToast(applicationContext, it.msg)
                        }
                        ResultStatus.SUCCESS.ordinal -> {
                            CommonMethods.hideLoader()
                            if (it.data != null) {
                                val response = it.data as TrackStatusResponse
                                CommonMethods.showToast(applicationContext, response.message)
                            }
                        }
                    }
                })
            }
        } else
            CommonMethods.showToast(applicationContext, getString(R.string.check_interent))
    }

    private fun createList(list: ArrayList<Data>) {
        if (list.size > 0) {
            val data: Data = list.get(0)
            shipment_id = data.shipment_id!!
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
            updateStatusAdapter.notifyDataSetChanged()


        }
    }


    private fun setAdapter() {
        binding.recyclerStatus.let {
            updateStatusAdapter = UpdateStatusAdapter(this@UpdateStatusActivity, list_status, this)
            it.layoutManager = LinearLayoutManager(this@UpdateStatusActivity)
            it.adapter = updateStatusAdapter
        }

    }

    override fun onUpdateStatus(position: Int) {
        list_status.get(position).status = "1"
        updateStatusAdapter.refresh()
        updateStatusResult()

    }
}