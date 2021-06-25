package com.google.mlkit.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.mlkit.showstatus.ShowStatusActivity
import com.google.mlkit.showstatus.UpdateStatusActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Constants
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityHomeBinding
import com.google.mlkit.vision.demo.kotlin.CameraXLivePreviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModel<HomeViewModel>()
    lateinit var binding: ActivityHomeBinding
    var isCustomer = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val startForResult =
            registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data != null && result.data!!.hasExtra("code")) {
                        val code = result.data!!.getStringExtra("code")
                        getStatus(code!!)
                    }
                }

            }

        binding.btnTrackShipment.setOnClickListener {
            isCustomer = true
            startForResult.launch(Intent(this, CameraXLivePreviewActivity::class.java))
        }
        binding.btnScancode.setOnClickListener {
            isCustomer = false
            startForResult.launch(Intent(this, CameraXLivePreviewActivity::class.java))
        }


    }

    private fun getStatus(shipment_id: String) {
        CommonMethods.showLog("==============", "=============getStatus${shipment_id}")
        if (CommonMethods.isNetworkAvailable(this)) {
            val trackStatusId = HomeRequest(shipment_id!!)
            homeViewModel.trackStatus(trackStatusId)
            if (!homeViewModel.trackStatusResponse.hasActiveObservers()) {
                homeViewModel.trackStatusResponse.observe(this, {
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
                            val trackStatusResponse = it?.data as TrackStatusResponse
                            val trackStatusData = trackStatusResponse.data
                            if (trackStatusData != null && trackStatusData.size > 0) {
                                if (isCustomer) {
                                    val intent = Intent(this, ShowStatusActivity::class.java)
                                    intent.putParcelableArrayListExtra(
                                        Constants.TRACK_STATUS_DATA,
                                        trackStatusData
                                    )
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, UpdateStatusActivity::class.java)
                                    intent.putParcelableArrayListExtra(
                                        Constants.TRACK_STATUS_DATA,
                                        trackStatusData
                                    )
                                    startActivity(intent)
                                }
                            } else {
                                CommonMethods.showToast(
                                    applicationContext,
                                    trackStatusResponse.message
                                )
                            }
                        }
                    }
                })
            }
        } else
            CommonMethods.showToast(applicationContext, getString(R.string.check_interent))
    }


}