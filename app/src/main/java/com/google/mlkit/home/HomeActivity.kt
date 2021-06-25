package com.google.mlkit.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import com.google.mlkit.showstatus.ShowStatusActivity
import com.google.mlkit.showstatus.UpdateStatusActivity
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityHomeBinding
import com.google.mlkit.vision.demo.kotlin.CameraXLivePreviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    val homeViewModel: HomeViewModel by viewModel<HomeViewModel>()
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val startForResult =
            registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data != null && result.data!!.hasExtra("code")) {
                        val code = result.data!!.getStringExtra("code")
                        getStatus(code)
                    }
                }

            }

        binding.btnScancode.setOnClickListener({
            startForResult.launch(Intent(this, CameraXLivePreviewActivity::class.java))
        })


    }

    private fun getStatus(shipment_id: String?) {
        Log.e("==============", "=============getStatus" + shipment_id)
//        startActivity(Intent(this,ShowStatusActivity::class.java))
        startActivity(Intent(this,UpdateStatusActivity::class.java))
    }
}