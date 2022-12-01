package dev.cnemo.personalaccounting.ui.dashboard

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.cnemo.personalaccounting.data.DatabaseController
import dev.cnemo.personalaccounting.models.AccountMove

class DashboardViewModel : ViewModel() {

    val accountMoves = MutableLiveData<MutableList<AccountMove>>()

    fun firstLoad(context: Context){
        DatabaseController.getInstance(context).getAllAccountMoves{ bulkLoad(it) }
    }
    fun bulkLoad(moves: MutableList<AccountMove>){
        accountMoves.value = moves
    }
    fun listenAccountMoves(context: Context) {
        DatabaseController.getInstance(context).listenAccountMoves {
            // TODO: Refactorizar
            firstLoad(context)
        }
    }

}