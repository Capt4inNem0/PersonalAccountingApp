package dev.cnemo.personalaccounting.models

import com.google.firebase.database.DataSnapshot
import dev.cnemo.personalaccounting.data.Date
import dev.cnemo.personalaccounting.data.Timestamp

data class PrestamoMove(var from: String, var to: String, var amount: Float, var timestamp: Timestamp, val date: Date) {
    constructor(): this("NULL", "NULL", Float.NaN, Timestamp(0L), Date(0L))
    companion object {
        fun parseFromSnapshot(snapshot: DataSnapshot): PrestamoMove?{
            return snapshot.getValue(PrestamoMove::class.java)
        }
    }
}