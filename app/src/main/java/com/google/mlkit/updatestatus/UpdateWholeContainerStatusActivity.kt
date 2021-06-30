package com.google.mlkit.updatestatus

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityUpdateWholeContainerStatusBinding
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateWholeContainerStatusActivity : AppCompatActivity() {
    private lateinit var list_codes: ArrayList<String>
    lateinit var binding: ActivityUpdateWholeContainerStatusBinding
    private var shipment_id: String = ""
    private var status_name: String = ""
    private val updateStatusViewModel: UpdateStatusViewModel by viewModel<UpdateStatusViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_update_whole_container_status)
        if (intent != null && intent.hasExtra("list_codes")) {
            list_codes = intent.getStringArrayListExtra("list_codes")!!
            for (code in list_codes) {
                if (shipment_id.isEmpty())
                    shipment_id = code
                else
                    shipment_id = shipment_id.plus(",$code")
            }
        }

        val list_status = ArrayList<String>()
        list_status.add("in_warehouse")
        list_status.add("en_route")
        list_status.add("arrived")
        list_status.add("pickup")
        list_status.add("completed")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, list_status
        )
        adapter.setDropDownViewResource(R.layout.spinnerview)
        binding.spinnerStatus.adapter = adapter

        binding.spinnerStatus.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                status_name = list_status.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnUpdatestatus.setOnClickListener {
            updateStatusHitApi()
        }
    }

    private fun updateStatusHitApi() {
        if (CommonMethods.isNetworkAvailable(this)) {
            val updateStatusRequest = UpdateWholeStatusRequest(
                shipment_id,
                status_name, "1"
            )
            updateStatusViewModel.updateWholeContainerStatus(updateStatusRequest)
            if (!updateStatusViewModel.statusWholeContainerResponse.hasObservers()) {
                updateStatusViewModel.statusWholeContainerResponse.observe(this, {
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
                                showAlertDialog(response.message)
                            }
                        }
                    }
                })
            }
        } else
            CommonMethods.showToast(applicationContext, getString(R.string.check_interent))
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(getString(R.string.ok)) { dialogInterface, which ->
            dialogInterface.cancel()
            setResult(RESULT_OK)
            finish()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}