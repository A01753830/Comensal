package mx.grm.prototipo4.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.FragmentSlideshowBinding


class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideshowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ratingBar = binding.caliLimpieza
        val ratingBar2 = binding.caliComida
        val ratingBar3 = binding.caliServicio

        val imageLimpieza = binding.imageLimpieza
        val imageCalidad = binding.imageCalidad
        val imageServ = binding.imageServ

        // Configurar registro
        setupRatingBar(ratingBar, imageLimpieza)
        setupRatingBar(ratingBar2, imageCalidad)
        setupRatingBar(ratingBar3, imageServ)
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