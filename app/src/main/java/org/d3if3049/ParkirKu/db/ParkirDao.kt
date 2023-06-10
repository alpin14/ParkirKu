package org.d3if3049.ParkirKu.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParkirDao{
    @Insert
    fun insert(warnet: ParkirEntity)

    @Query("SELECT * FROM parkir ORDER BY id DESC")
    fun getLastWarnet(): LiveData<List<ParkirEntity?>>

    @Query("DELETE FROM parkir")
    fun clearData()

    @Query("DELETE FROM parkir WHERE id = :id")
    fun deleteHistory(id: Long)
}