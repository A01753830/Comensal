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
 * Menu Frag View
 * @author Héctor González Sánchez
 */


class GalleryFragment : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
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
                //COSO QUE NO SÉ
                println("No hay datos aún")
            }
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

    override fun onStart() {
        super.onStart()
        viewModel.downloadDiningNames()
    }
}