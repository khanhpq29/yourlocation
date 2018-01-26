package pq.khanh.vn.yournearby.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by khanhpq on 1/25/18.
 */
data class Book(var total: Int, val name: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(total)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Book> = object : Parcelable.Creator<Book> {
            override fun createFromParcel(source: Parcel): Book = Book(source)
            override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
        }
    }
}