package com.google.mlkit.home

data class TrackStatusResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)

data class Data(
    val arrived: String,
    val completed: String,
    val en_route: String,
    val id: String,
    val in_warehouse: String,
    val pickup: String,
    val shipment_id: String
)