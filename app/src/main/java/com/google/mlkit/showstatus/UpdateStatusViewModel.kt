package com.google.mlkit.showstatus

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UpdateStatusViewModel @Inject constructor(val updateStatusViewModelRepository: UpdateStatusViewModelRepository) : ViewModel() {
    lateinit var re
}