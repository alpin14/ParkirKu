package org.d3if3049.ParkirKu.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parkir")
data class ParkirEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var jam: Int,
    var tipe: String
)