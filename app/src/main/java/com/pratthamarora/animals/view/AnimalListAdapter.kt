package com.pratthamarora.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.animals.R
import com.pratthamarora.animals.model.Animals
import com.pratthamarora.animals.utils.getProgressDrawable
import com.pratthamarora.animals.utils.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animals>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {
    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun updateAnimalList(newAnimalList: List<Animals>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.itemView.animalName.text = animalList[position].name
        holder.itemView.animalImage.loadImage(
            animalList[position].imageUrl,
            getProgressDrawable(holder.itemView.context)
        )
        holder.itemView.animalLayout.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToDetailFragment(animalList[position])
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }
}