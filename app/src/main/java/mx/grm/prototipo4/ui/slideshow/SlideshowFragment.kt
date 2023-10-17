package mx.grm.prototipo4.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.FragmentSlideshowBinding
import mx.grm.prototipo4.model.vulCondAdapter
import org.w3c.dom.Comment

/**
 * Survey Frag View
 * @author Héctor González Sánchez
 */


class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private val viewModel: SlideshowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideshowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ratingBar = binding.rbQuest1
        val ratingBar2 = binding.rbQuest2
        val ratingBar3 = binding.rbQuest3

        val imageLimpieza = binding.imageLimpieza
        val imageCalidad = binding.imageCalidad
        val imageServ = binding.imageServ

        // Configurar registro
        setupRatingBar(ratingBar, imageLimpieza)
        setupRatingBar(ratingBar2, imageCalidad)
        setupRatingBar(ratingBar3, imageServ)

        setUpListeners()
        uploadSurvey()
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

    private fun setUpListeners() {

        viewModel.diningNames.observe(viewLifecycleOwner) { list ->
            val arrNames = list.toTypedArray()

            binding.spDiningS.adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, arrNames)
        }
    }

    private fun uploadSurvey() {
        binding.btnUploadSurvey.setOnClickListener {
            if (binding.rbQuest1.rating != 0.0f &&
                binding.rbQuest2.rating != 0.0f &&
                binding.rbQuest3.rating != 0.0f){

                val diningName = binding.spDiningS.selectedItem.toString()
                getQuestion1(diningName)
                getQuestion2(diningName)
                getQuestion3(diningName)
                Toast.makeText(requireActivity(), "Gracias!", Toast.LENGTH_SHORT).show()
                // Clean texts
                cleanSurvey()
            } else {
                Toast.makeText(requireActivity(), "Recuerda contestar todas las preguntas", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getQuestion1(diningName: String){
        val quest = binding.etQuest1.text.toString()
        val oldComment = binding.etComt1.text.toString()
        val comment = emptyComment(oldComment)
        val score = binding.rbQuest1.rating

        return viewModel.uploadSurvey(diningName, quest, comment, score)
    }

    private fun getQuestion2(diningName: String){
        val quest = binding.etQuest2.text.toString()
        val oldComment = binding.etComt2.text.toString()
        val comment = emptyComment(oldComment)
        val score = binding.rbQuest2.rating

        return viewModel.uploadSurvey(diningName, quest, comment, score)
    }

    private fun getQuestion3(diningName: String){
        val quest = binding.etQuest3.text.toString()
        val oldComment = binding.etComt3.text.toString()
        val comment = emptyComment(oldComment)
        val score = binding.rbQuest3.rating

        return viewModel.uploadSurvey(diningName, quest, comment, score)
    }

    private fun emptyComment(comment: String): String{
        if (comment == ""){
            return "N/A"
        }
        return comment
    }

    private fun cleanSurvey(){
        binding.etComt1.text?.clear()
        binding.etComt2.text?.clear()
        binding.etComt3.text?.clear()

        binding.rbQuest1.rating = 0.0f
        binding.rbQuest2.rating = 0.0f
        binding.rbQuest3.rating = 0.0f

        binding.imageCalidad.setImageResource(R.drawable.normal)
        binding.imageLimpieza.setImageResource(R.drawable.normal)
        binding.imageServ.setImageResource(R.drawable.normal)
    }

    override fun onStart() {
        super.onStart()
        viewModel.downloadDiningNames()
    }
}