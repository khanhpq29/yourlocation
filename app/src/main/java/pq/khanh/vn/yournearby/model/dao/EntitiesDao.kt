package pq.khanh.vn.yournearby.model.dao

import android.arch.persistence.room.*
import io.reactivex.Maybe
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.model.Entities

/**
 * Created by khanhpq on 2/1/18.
 */
@Dao
interface EntitiesDao {
    @Query("SELECT * FROM entity")
    fun getAllEntities() : Maybe<MutableList<Entities>>

    @Insert
    fun insertNewEntity(entities: Entities)

    @Delete
    fun deleteEntity(entities: Entities)

    @Query("SELECT * FROM entity WHERE id = :userId")
    fun getUser(userId : Int) : Maybe<Entities>
}