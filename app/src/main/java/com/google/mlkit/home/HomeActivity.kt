package com.google.mlkit.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.mlkit.laguage.SelectLanguageActivity
import com.google.mlkit.login.LoginActivity
import com.google.mlkit.showstatus.ShowStatusActivity
import com.google.mlkit.updatestatus.UpdateStatusActivity
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
    private var isCustomer = false
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

        binding.btnScanWholeContainer.setOnClickListener {
            val intent = Intent(this, CameraXLivePreviewActivity::class.java)
            intent.putExtra("scanWholeContainer", true)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.homemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_lang -> {
                val intent = Intent(this, SelectLanguageActivity::class.java)
                intent.putExtra("isFromSplash", false)
                startActivity(intent)
            }
            R.id.logout -> {
                CommonMethods.clearSharedPref(this)
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
        return true

    }

    private fun getStatus(shipment_id: String) {
        CommonMethods.showLog("==============", "=============getStatus${shipment_id}")
        if (CommonMethods.isNetworkAvailable(this)) {
            val trackStatusId = HomeRequest(shipment_id)
            homeViewModel.trackStatus(trackStatusId)
            if (!homeViewModel.trackStatusResponse.hasObservers()) {
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
                                    intent.putExtra(Constants.SHIPMENT_ID, shipment_id)
                                    intent.putParcelableArrayListExtra(
                                        Constants.TRACK_STATUS_DATA,
                                        trackStatusData
                                    )
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, UpdateStatusActivity::class.java)
                                    intent.putExtra(Constants.SHIPMENT_ID, shipment_id)
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