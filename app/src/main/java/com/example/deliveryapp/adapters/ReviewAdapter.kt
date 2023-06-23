package com.example.deliveryapp.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapp.databinding.ReviewListItemBinding
import com.example.deliveryapp.models.Review
import com.example.deliveryapp.url


class ReviewAdapter(val context : Context): RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    var data = mutableListOf<Review>();

    fun setReviews(reviews: List<Review>) {
        this.data = reviews.toMutableList();
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.MyViewHolder {
        return ReviewAdapter.MyViewHolder(
            ReviewListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ReviewAdapter.MyViewHolder, position: Int) {

        holder.binding.apply {
            Glide.with(context).load(url + data[position].image_url).into(userImg)
            username.text = data[position].username
            content.text = data[position].content

        }
    }

    class MyViewHolder(val binding: ReviewListItemBinding) : RecyclerView.ViewHolder(binding.root)


}
