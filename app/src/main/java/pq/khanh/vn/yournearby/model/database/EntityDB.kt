package pq.khanh.vn.yournearby.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.model.Entities
import pq.khanh.vn.yournearby.model.dao.EntitiesDao

/**
 * Created by khanhpq on 2/1/18.
 */
@Database(entities = [Book::class], version = 1)
abstract class EntityDB : RoomDatabase(){
    abstract fun entitiesDb() : EntitiesDao
}