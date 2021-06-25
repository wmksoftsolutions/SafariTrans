package com.google.mlkit.showstatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.home.Data
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Constants
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityUpdateStatusBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateStatusBinding
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
        }

        updateStatusResult()
    }

    private fun updateStatusResult() {
        if (CommonMethods.isNetworkAvailable(this)) {
            val updateStatusRequest = UpdateStatusRequest()
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

                        }
                    }
                })
            }
        } else
            CommonMethods.showToast(applicationContext, getString(R.string.check_interent))
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