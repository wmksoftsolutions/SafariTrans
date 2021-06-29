package com.google.mlkit.updatestatus

data class UpdateWholeStatusRequest(
    var shipment_id: String,
    var status_name: String,
    var status_value: String
)