package com.pratthamarora.animals.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pratthamarora.animals.R
import com.pratthamarora.animals.model.Animals
import com.pratthamarora.animals.utils.getProgressDrawable
import com.pratthamarora.animals.utils.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_animal.animalImage
import kotlinx.android.synthetic.main.item_animal.animalName

class DetailFragment : Fragment() {

    var animals: Animals? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animals = DetailFragmentArgs.fromBundle(it).animal   //retrieving data
        }

        context?.let { getProgressDrawable(it) }?.let {
            animalImage.loadImage(
                animals?.imageUrl,
                it
            )
        }
        animalName.text = animals?.name
        animalLocation.text = animals?.location
        animalLifeSpan.text = animals?.lifeSpan
        animalDiet.text = animals?.diet

    }

}
