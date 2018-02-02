package pq.khanh.vn.yournearby.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by khanhpq on 2/1/18.
 */
@Entity(tableName = "entity")
open class Entities(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "Total")
        var total: Int = 0,
        @ColumnInfo(name = "Name")
        var name: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(total)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Entities> = object : Parcelable.Creator<Entities> {
            override fun createFromParcel(source: Parcel): Entities = Entities(source)
            override fun newArray(size: Int): Array<Entities?> = arrayOfNulls(size)
        }
    }
}