package com.levytskyi.example.myfooddelivery.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.levytskyi.example.myfooddelivery.DetailsActivity
import com.levytskyi.example.myfooddelivery.Models.PopularModel
import com.levytskyi.example.myfooddelivery.Models.SharedModel
import com.levytskyi.example.myfooddelivery.databinding.HomeFoodItemBinding

class PopularAdapter(
    val context: Context,
    var list: ArrayList<PopularModel>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private lateinit var sharedModel: SharedModel

    fun setSharedModel(videoModel: SharedModel) {
        sharedModel = videoModel
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.PopularViewHolder {
        val binding = HomeFoodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {

        val listModel = list[position]

        holder.foodName.text = listModel.getFoodName()
        holder.foodPrice.text = listModel.getFoodPrice().toString()
        listModel.getFoodImage()?.let { holder.foodImage.setImageResource(it) }

        holder.item.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("foodImage", listModel.getFoodImage())
            intent.putExtra("foodName", listModel.getFoodName())
            context.startActivity(intent)

        }

        holder.addBtn.setOnClickListener {

            if (sharedModel.inList(listModel)) {
                sharedModel.deleteFromCart(listModel)
                holder.addBtn.setText("Add To Cart")
            } else {
                sharedModel.addToCart(listModel)
                holder.addBtn.setText("Delete Cart")
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PopularViewHolder(binding: HomeFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {


        val foodImage = binding.homeFoodImage
        val foodName = binding.homeFoodName
        val foodPrice = binding.homeFoodPrice

        val addBtn = binding.homeFoodBtn
        val item = binding.root
    }

    fun updateList(newList: ArrayList<PopularModel>) {
        list = newList
        notifyDataSetChanged()
    }

}