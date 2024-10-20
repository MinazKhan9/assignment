package com.example.myassignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myassignment.Model.Article
import com.example.myassignment.R
import com.google.android.material.imageview.ShapeableImageView

class ArticleAdapter(private val articleList : ArrayList<Article>): RecyclerView.Adapter<ArticleAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articleList[position]
        holder.featuredImage.setImageResource(currentItem.featuredImage)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val featuredImage : ImageView = itemView.findViewById(R.id.featuredImage)
        val title : TextView = itemView.findViewById(R.id.title)
        val description : TextView = itemView.findViewById(R.id.description)

    }
}