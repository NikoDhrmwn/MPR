package com.example.labmpr4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adaptor(private val listAnime: ArrayList<anime>):
    RecyclerView.Adapter<adaptor.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvNameAnime.text = listAnime[position].id.toString()
        holder.tvDescriptionAnime.text = listAnime[position].name

        val img = listAnime[position].img

        Picasso.get().load(img).into(holder.imgAnime)

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listAnime[holder.adapterPosition])
        }

    //    val (nama, description, photo) = listAnime[position]
    //    holder.imgAnime.setImageResource(photo)
    //    holder.tvNameAnime.text = nama
    //    holder.tvDescriptionAnime.text = description
    }

    override fun getItemCount(): Int = listAnime.size

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgAnime: ImageView = itemView.findViewById(R.id.imageView)
        var tvNameAnime: TextView = itemView.findViewById(R.id.textView)
        var tvDescriptionAnime: TextView = itemView.findViewById(R.id.textView2)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: anime)
    }
}
