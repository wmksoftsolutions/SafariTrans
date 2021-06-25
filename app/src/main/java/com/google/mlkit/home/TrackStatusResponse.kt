package com.google.mlkit.home

import android.os.Parcel
import android.os.Parcelable

data class TrackStatusResponse(
    val `data`: ArrayList<Data>,
    val message: String?,
    val status: Boolean
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readArrayList(Data.CREATOR),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeByte(if (status) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrackStatusResponse> {
        override fun createFromParcel(parcel: Parcel): TrackStatusResponse {
            return TrackStatusResponse(parcel)
        }

        override fun newArray(size: Int): Array<TrackStatusResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class Data(
    val arrived: String?,
    val completed: String?,
    val en_route: String?,
    val id: String?,
    val in_warehouse: String?,
    val pickup: String?,
    val shipment_id: String?
):Parcelable{
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