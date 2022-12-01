package dev.cnemo.personalaccounting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.cnemo.personalaccounting.R
import dev.cnemo.personalaccounting.models.PrestamoMove

class PrestamoMoveLineAdapter (private val lines: MutableList<PrestamoMove>) :
    RecyclerView.Adapter<PrestamoMoveLineAdapter.PrestamoMoveViewHolder>() {

    fun setData(newLines: MutableList<PrestamoMove>){
        lines.clear()
        lines.addAll(newLines)
        lines.sortBy { it.date }
        notifyDataSetChanged()
//        for (line in newLines) {
//            lines.add(line)
//            notifyItemInserted(newLines.size - 1)
//        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoMoveViewHolder {
        return  PrestamoMoveViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line_account_move,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PrestamoMoveViewHolder, position: Int) {
        holder.bind(lines[position])
    }

    override fun getItemCount(): Int = lines.size

    inner class PrestamoMoveViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(line: PrestamoMove){

        }
    }

}
