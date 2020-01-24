package com.pratthamarora.animals.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

        animals?.imageUrl?.let { setupBGColor(it) }
    }

    private fun setupBGColor(imageUrl: String) {
        Glide.with(this).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource).generate { palette ->
                    val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                    animalDetailLayout.setBackgroundColor(intColor)

                }
            }

        })
    }

}
