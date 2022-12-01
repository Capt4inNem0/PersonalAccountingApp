package dev.cnemo.personalaccounting.models

import com.google.firebase.database.DataSnapshot
import dev.cnemo.personalaccounting.data.Date
import dev.cnemo.personalaccounting.data.Timestamp

data class AccountMove(var partner: String, var partnerId: String, var amount: Float, var timestamp: Timestamp, val date: Date) {
    constructor(): this("NULL", "NULL", Float.NaN, Timestamp(0L), Date(0L))
    companion object {
        fun parseFromSnapshot(snapshot: DataSnapshot): AccountMove?{
            return snapshot.getValue(AccountMove::class.java)
        }
    }
}