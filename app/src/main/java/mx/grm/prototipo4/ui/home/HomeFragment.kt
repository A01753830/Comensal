package mx.grm.prototipo4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.FragmentHomeBinding
import mx.grm.prototipo4.model.QrManager
import mx.grm.prototipo4.model.vulCondAdapter

/**
 * Customer's auto-registration Frag View
 * @author Héctor González Sánchez
 */


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
        setUpListeners()
        scanQR()
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

            viewModel.uploadCostumer(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation) {success ->
                if (success) {
                    val token = viewModel.customerToken.value
                    if (!token.isNullOrBlank()){
                        val alert = AlertDialog.Builder(requireActivity())
                            .setTitle("T O K E N")
                            .setMessage("Se le proporciona el siguiente token: ${token}")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar"){_, _ ->
                                cleanRegistration()
                            }
                        alert.show()
                    } else {
                        println("ERROR")
                    }
                }
            }
        }
    }

    private fun setUpListeners(){

        binding.tvHelpSuper.setOnClickListener {
            val alert = AlertDialog.Builder(requireActivity())
                .setTitle("~Contáctanos Directamente~")
                .setMessage("Nombre: Héctor \n" +
                        "Apellido: Sánchez \n" +
                        "Correo: benny_gs@comedoresdif.com")
                .setCancelable(false)
                .setPositiveButton("Continuar", null)
            alert.show()
        }
    }

    private fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }

    private fun cleanRegistration() {
        binding.etName.text?.clear()
        binding.etPLastName.text?.clear()
        binding.etMLastName.text?.clear()
        binding.etCurp.text?.clear()
        binding.etBDate.text?.clear()
        //CLEAN SPINNER, RECYCLERVIEW
    }

    private fun scanQR() {
        binding.btnRegQr.setOnClickListener {
            QrManager.startQrCodeScanner(requireActivity(), { result ->

                val regex = """\|+""".toRegex()
                val regexRes = regex.split(result)
                println(result)
                println(regexRes.size)
                // No repetitions
                if(regexRes.size == 9) {
                    binding.etCurp.setText(regexRes[0])
                    binding.etPLastName.setText(regexRes[1])
                    binding.etMLastName.setText(regexRes[2])
                    binding.etName.setText(regexRes[3])
                    if(regexRes[4] == "HOMBRE") {
                        binding.spGender.setSelection(0)
                    } else {
                        binding.spGender.setSelection(1)
                    }
                    val dateParts = regexRes[5].split("/")
                    val year = dateParts.last()
                    binding.etBDate.setText(year)

                } else { // Repetitions
                    binding.etCurp.setText(regexRes[0])
                    binding.etPLastName.setText(regexRes[2])
                    binding.etMLastName.setText(regexRes[3])
                    binding.etName.setText(regexRes[4])
                    if(regexRes[5] == "HOMBRE") {
                        binding.spGender.setSelection(0)
                    } else {
                        binding.spGender.setSelection(1)
                    }
                    val dateParts = regexRes[6].split("/")
                    val year = dateParts.last()
                    binding.etBDate.setText(year)
                } },

                { error ->
                    println(error.message) }
            )
        }
    }
}