package mx.grm.prototipo4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.FragmentHomeBinding
import mx.grm.prototipo4.model.vulCondAdapter

class HomeFragment : Fragment() {

    // ViewModel y Binding
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: vulCondAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getVulSituations()
        uploadCustomer()
        viewModel.getVulSituations()
    }

    private fun getVulSituations() {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rvVulSituations.layoutManager = layout

        viewModel.vulCondList.observe(viewLifecycleOwner) {vulCondList ->
            val arrVulCond = vulCondList.toTypedArray()
            adapter = vulCondAdapter(requireContext(), arrVulCond)
            binding.rvVulSituations.adapter = adapter

            adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    val selectedConditions = adapter?.selectedConditions
                    println("Condiciones seleccionadas: $selectedConditions")
                }
            })
        }
    }

    private fun uploadCustomer() {
        binding.btnUploadCostumer.setOnClickListener {
            val name = binding.etName.text.toString()
            val p_lastName = binding.etPLastName.text.toString()
            val m_lastName = binding.etMLastName.text.toString()
            val curp = binding.etCurp.text.toString()
            val bDate = binding.etBDate.text.toString()
            val gender = binding.spGender.selectedItem.toString()
            val vulSituation:Array<String> = getCond()

            viewModel.uploadCostumer(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)
        }
    }

    private fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }
}