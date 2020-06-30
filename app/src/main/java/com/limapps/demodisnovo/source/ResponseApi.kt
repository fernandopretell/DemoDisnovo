package com.limapps.demodisnovo.source

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList


data class ResponseApi(val code: String,
                       val data: Data,
                       val message: String)

data class Data(val elements: ArrayList<Elements>?, val id: Int):Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Elements),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(elements)
        parcel.writeInt(id)
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

data class Elements(val component_type: String?,
                    val field_name_key: String?,
                    val field_name_view: String?,
                    val ordinal: Int,
                    val min: Int = 0,
                    val max:Int = 0,
                    val value:String? = ""):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeString(component_type)
            dest.writeString(field_name_key)
            dest.writeString(field_name_view)
            dest.writeInt(ordinal)
            dest.writeInt(min)
            dest.writeInt(max)
            dest.writeString(value)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Elements> {
        override fun createFromParcel(parcel: Parcel): Elements {
            return Elements(parcel)
        }

        override fun newArray(size: Int): Array<Elements?> {
            return arrayOfNulls(size)
        }
    }
}



