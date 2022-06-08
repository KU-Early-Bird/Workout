package com.example.teampel_1.ui.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teampel_1.R

class WorkoutAdapter (val items:ArrayList<WorkoutData>): RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClickListener(data:WorkoutData)
    }

    var itemClickListener: AdapterView.OnItemClickListener?=null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.workoutRow)
        init {
            textView.setOnClickListener {
//                itemClickListener?.onItemClickListener(items[adapterPosition])
            }
        }
    }

    fun moveItem(oldPos: Int, newPos:Int){
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_row, parent, false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
    }

    override fun getItemCount(): Int {
        return items.size
    }


}