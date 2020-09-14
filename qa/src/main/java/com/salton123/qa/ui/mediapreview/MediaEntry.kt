package com.salton123.qa.ui.mediapreview

import android.os.Parcel
import android.os.Parcelable

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/11 16:35
 * ModifyTime: 16:35
 * Description:
 */
const val TYPE_IMAGE = 0
const val TYPE_VIDEO = 1

data class MediaEntry(
        var type: Int = TYPE_IMAGE,
        var thumbnailUrl: String? = "",
        var mediaUrl: String? = "",
        var photoUrl: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    fun isVideoType(): Boolean {
        return type == TYPE_VIDEO
    }

    fun isImageType(): Boolean {
        return type == TYPE_IMAGE
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(type)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(mediaUrl)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaEntry> {
        override fun createFromParcel(parcel: Parcel): MediaEntry {
            return MediaEntry(parcel)
        }

        override fun newArray(size: Int): Array<MediaEntry?> {
            return arrayOfNulls(size)
        }
    }
}