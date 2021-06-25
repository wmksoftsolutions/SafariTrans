package com.google.mlkit.home

import android.os.Parcel
import android.os.Parcelable

data class TrackStatusResponse(
    val data: ArrayList<Data>?,
    val message: String,
    val status: Boolean
)

data class Data(
    val arrived: String?,
    val completed: String?,
    val en_route: String?,
    val id: String?,
    val in_warehouse: String?,
    val pickup: String?,
    val shipment_id: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(arrived)
        parcel.writeString(completed)
        parcel.writeString(en_route)
        parcel.writeString(id)
        parcel.writeString(in_warehouse)
        parcel.writeString(pickup)
        parcel.writeString(shipment_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }

}