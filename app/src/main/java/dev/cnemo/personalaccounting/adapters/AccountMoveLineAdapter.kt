package dev.cnemo.personalaccounting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.cnemo.personalaccounting.R
import dev.cnemo.personalaccounting.models.AccountMove
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class AccountMoveLineAdapter (private val lines: MutableList<AccountMove>) :
    RecyclerView.Adapter<AccountMoveLineAdapter.AccountMoveViewHolder>() {

    fun setData(newLines: MutableList<AccountMove>){
        lines.clear()
        lines.addAll(newLines)
        lines.sortBy { it.date }
        notifyDataSetChanged()
//        for (line in newLines) {
//            lines.add(line)
//            notifyItemInserted(newLines.size - 1)
//        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountMoveViewHolder {
        return  AccountMoveViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line_account_move,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccountMoveViewHolder, position: Int) {
        holder.bind(lines[position])
    }

    override fun getItemCount(): Int = lines.size

    inner class AccountMoveViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(line: AccountMove){
            "$ ${line.amount}".also { itemView.findViewById<TextView>(R.id.line_account_move_amount).text = it }
            itemView.findViewById<TextView>(R.id.line_account_move_date).text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(line.date)
            itemView.findViewById<TextView>(R.id.line_account_move_partner).text = line.partner
        }
    }

}
