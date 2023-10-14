package mx.grm.prototipo4.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.FragmentSlideshowBinding
import mx.grm.prototipo4.databinding.FragmentSlideshowBinding.inflate


class SlideshowFragment : Fragment() {

    private lateinit var ratingBar: RatingBar
    private lateinit var ratingBar2: RatingBar
    private lateinit var ratingBar3: RatingBar
    private lateinit var imageLimpieza: ImageView
    private lateinit var imageCalidad: ImageView
    private lateinit var imageServicio: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_slideshow, container, false)
        ratingBar = view.findViewById(R.id.caliLimpieza)
        ratingBar2 = view.findViewById(R.id.caliComida)
        ratingBar3 = view.findViewById(R.id.caliServicio)

        imageLimpieza = view.findViewById(R.id.imageLimpieza)
        imageCalidad = view.findViewById(R.id.imageCalidad)
        imageServicio = view.findViewById(R.id.imageServicio)

        // Configurar registro
        setupRatingBar(ratingBar, imageLimpieza)
        setupRatingBar(ratingBar2, imageCalidad)
        setupRatingBar(ratingBar3, imageServicio)

        return view
    }

    private fun setupRatingBar(ratingBar: RatingBar, imageView: ImageView) {
        ratingBar.numStars = 5
        ratingBar.stepSize = 1.0f
        ratingBar.rating = 0.0f

        // Cambio de imágenes
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            when (rating) {
                5.0f -> imageView.setImageResource(R.drawable.feliz)
                4.0f -> imageView.setImageResource(R.drawable.feliz2)
                3.0f -> imageView.setImageResource(R.drawable.normal)
                2.0f -> imageView.setImageResource(R.drawable.triste)
                1.0f -> imageView.setImageResource(R.drawable.muy_triste)
            }
        }
    }
}