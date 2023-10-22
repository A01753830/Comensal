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
 * SlideshowFragment - A Fragment for conducting a survey.
 *
 * This Fragment allows users to answer survey questions related to the quality of a dining experience.
 * Users can rate different aspects of the experience and provide optional comments. The responses
 * are associated with a specific dining location.
 *
 * @property binding - An instance of the FragmentSlideshowBinding class for managing the layout.
 * @property viewModel - An instance of SlideshowViewModel for handling data and logic.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private val viewModel: SlideshowViewModel by viewModels()

    // Initialize FragmentSlideshowBinding and SlideshowViewModel.
    // ...

    /**
     * Called when the Fragment is created. Inflates the layout defined in FragmentSlideshowBinding.
     *
     * @param inflater - The LayoutInflater used to inflate the layout.
     * @param container - The parent view that the Fragment's UI should be attached to.
     * @param savedInstanceState - A Bundle containing saved state information.
     * @return The root View of the Fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using FragmentSlideshowBinding and return the root view.
        binding = FragmentSlideshowBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * Called after the view is created. Initializes UI elements, sets up event listeners,
     * and triggers data loading.
     *
     * @param view - The root View of the Fragment.
     * @param savedInstanceState - A Bundle containing saved state information.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ratingBar = binding.rbQuest1
        val ratingBar2 = binding.rbQuest2
        val ratingBar3 = binding.rbQuest3

        val imageLimpieza = binding.imageLimpieza
        val imageCalidad = binding.imageCalidad
        val imageServ = binding.imageServ

        // Setup rating bars
        setupRatingBar(ratingBar, imageLimpieza)
        setupRatingBar(ratingBar2, imageCalidad)
        setupRatingBar(ratingBar3, imageServ)

        setUpListeners()
        uploadSurvey()
    }

    /**
     * Sets up the RatingBar widget and associates it with an ImageView to change images based on ratings.
     *
     * @param ratingBar - The RatingBar widget to configure.
     * @param imageView - The ImageView to change based on RatingBar ratings.
     */
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

    /**
     * Sets up listeners to respond to changes in the list of dining locations.
     */
    private fun setUpListeners() {

        viewModel.diningNames.observe(viewLifecycleOwner) { list ->
            val arrNames = list.toTypedArray()

            binding.spDiningS.adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, arrNames)
        }
    }


    /**
     * Handles the user's survey submission, including ratings, comments, and dining location.
     */
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

    /**
     * Retrieves data related to the user's response to the first survey question.
     *
     * @param diningName - The selected dining location.
     */
    private fun getQuestion1(diningName: String){
        val quest = binding.etQuest1.text.toString()
        val oldComment = binding.etComt1.text.toString()
        val comment = emptyComment(oldComment)
        val score = binding.rbQuest1.rating

        return viewModel.uploadSurvey(diningName, quest, comment, score)
    }

    // Similar functions for getting data for the second and third survey questions.
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

    /**
     * Ensures that an empty comment is represented as "N/A".
     *
     * @param comment - The user's comment.
     * @return The comment or "N/A" if it is empty.
     */
    private fun emptyComment(comment: String): String{
        if (comment == ""){
            return "N/A"
        }
        return comment
    }

    /**
     * Resets the survey UI elements, clearing user inputs and resetting ratings and images.
     */
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

    /**
     * Called when the Fragment is started. Initiates the process of downloading dining location names.
     */
    override fun onStart() {
        super.onStart()
        viewModel.downloadDiningNames()
    }
}