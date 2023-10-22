package mx.grm.prototipo4.ui.gallery

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mx.grm.prototipo4.databinding.FragmentGalleryBinding

/**
 * GalleryFragment - Fragment for displaying menu and dining status information.
 *
 * This Fragment is responsible for displaying menu and dining status information for different dining
 * locations. Users can select a dining location from a spinner, and the corresponding menu and dining status
 * data is displayed.
 *
 * @property binding - An instance of the FragmentSlideshowBinding class for managing the layout.
 * @property viewModel - An instance of SlideshowViewModel for handling data and logic.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class GalleryFragment : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    /**
     * Called when the Fragment is created. Inflates the layout defined in FragmentGalleryBinding.
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
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * Called after the view is created. Initializes UI elements and sets up event listeners.
     *
     * @param view - The root View of the Fragment.
     * @param savedInstanceState - A Bundle containing saved state information.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    /**
     * Sets up event listeners for UI elements, such as the spinner for selecting dining locations.
     * Observes changes in data from the ViewModel and updates the UI accordingly.
     */
    private fun setUpListeners() {
        // Observe changes in the list of dining names and populate the spinner.
        viewModel.diningNames.observe(viewLifecycleOwner){ list ->
            val arrNames = list.toTypedArray()

            binding.spDining.adapter = ArrayAdapter(requireContext(),
                R.layout.simple_spinner_item, arrNames)

        binding.spDining.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedName = arrNames[position]
                viewModel.getMenu(selectedName)
                viewModel.getDiningStatus(selectedName)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("No hay datos aún")
            }
        }

            viewModel.menuMessage.observe(viewLifecycleOwner) { value ->
                binding.etMenuMessage.text = value.toString()
            }

            viewModel.soup.observe(viewLifecycleOwner) { value ->
                binding.etSoup.text = value.toString()
            }

            viewModel.mainCourse.observe(viewLifecycleOwner) { value ->
                binding.etMainCourse.text = value.toString()
            }

            viewModel.carbs.observe(viewLifecycleOwner) { value ->
                binding.etCarbs.text = value.toString()
            }

            viewModel.water.observe(viewLifecycleOwner) { value ->
                binding.etWater.text = value.toString()
            }

            viewModel.beansSauce.observe(viewLifecycleOwner) { value ->
                binding.etBeansSauce.text = value.toString()
            }

            viewModel.diningStatus.observe(viewLifecycleOwner) {status ->
                binding.etDiningStatus.text = status.toString()
            }
        }
    }

    /**
     * Called when the Fragment starts. Downloads the list of dining location names from the ViewModel.
     */
    override fun onStart() {
        super.onStart()
        viewModel.downloadDiningNames()
    }
}