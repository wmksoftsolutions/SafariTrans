package com.google.mlkit.updatestatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityUpdateWholeContainerStatusBinding
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

        /*    binding.spinnerStatus.onItemSelectedListener(object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            })
            binding.btnUpdatestatus.setOnClickListener {
                updateStatusHitApi()
            }
    */
    }

    private fun updateStatusHitApi() {
        if (CommonMethods.isNetworkAvailable(this)) {
            val updateStatusRequest = UpdateWholeStatusRequest(
                shipment_id,
                "", "1"
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
                                CommonMethods.showToast(applicationContext, response.message)
                            }
                        }
                    }
                })
            }
        } else
            CommonMethods.showToast(applicationContext, getString(R.string.check_interent))
    }
}