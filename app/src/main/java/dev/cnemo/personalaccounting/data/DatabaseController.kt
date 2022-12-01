package dev.cnemo.personalaccounting.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.database.*
import dev.cnemo.personalaccounting.models.AccountMove
import dev.cnemo.personalaccounting.models.PrestamoMove

import java.util.*

class DatabaseController {
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference = database.getReference("/")

    private fun insertData(path: String, data: Map<String, Any>){
        reference.child(path).setValue(data)
    }

    fun pushAccountMove(desc: String, amount: Float, partner: String, partnerId: String, date: Date){
        insertData("account/moves/${UUID.randomUUID()}", mapOf("amount" to amount, "desc" to desc, "partner" to partner, "partnerId" to partnerId, "date" to date, "timestamp" to Timestamp.now()))
    }

    fun listenAccountMoves(callback: () -> Unit){
        reference.child("account/moves").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }

    fun getAllAccountMoves(callback : (MutableList<AccountMove>)-> Unit) {
        reference.child("account/moves").get().addOnSuccessListener { dataSnapshot ->
            callback(dataSnapshot.children.mapNotNull { AccountMove.parseFromSnapshot(it) }.toMutableList())
        }
    }

    fun getAllPrestamoMoves(callback : (MutableList<PrestamoMove>)-> Unit) {
        reference.child("account/prestamos").get().addOnSuccessListener { dataSnapshot ->
            callback(dataSnapshot.children.mapNotNull { PrestamoMove.parseFromSnapshot(it) }.toMutableList())
        }
    }

    companion object {

        private var instance: DatabaseController? = null

        fun getInstance(context: Context): DatabaseController {
            FirebaseApp.initializeApp(context)
            val instance = instance
            return if (instance == null){
                this.instance = DatabaseController()
                getInstance(context)
            } else {
                instance
            }
        }


    }

}