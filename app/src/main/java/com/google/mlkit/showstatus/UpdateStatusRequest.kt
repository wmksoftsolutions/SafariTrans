package com.google.mlkit.showstatus

data class UpdateStatusRequest(var shipment_id: String,var in_warehouse:String, var en_route:String,var arrived:String,var pickup:String, var completed :String ) {
    constructor() : this("","","","","","")
}