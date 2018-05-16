package se.xlntech.blockchain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var items: ArrayList<Block> = ArrayList()

    fun updateListItems(items: ArrayList<Block>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.description.text = items[position].data
        holder.code.text = items[position].hash
    }

    inner class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var description = view.findViewById<TextView>(R.id.description)!!
        var code = view.findViewById<TextView>(R.id.hashCode)!!
    }
}