package mx.grm.prototipo4.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
import mx.grm.prototipo4.model.responses.vulCondItem
import mx.grm.prototipo4.model.vulCondAdapter

/**
 * HomeFragment - Fragment for Customer's Auto-Registration.
 *
 * This Fragment is responsible for the auto-registration of customers. It allows users to input their
 * registration data, select vulnerable conditions, and initiate QR code scanning. It also provides links
 * to privacy notices and additional help.
 *
 * @property binding - An instance of the FragmentSlideshowBinding class for managing the layout.
 * @property viewModel - An instance of SlideshowViewModel for handling data and logic.
 * @property adapter - An instance fo the vulCondAdapter class for populating the recyclerview
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */


class HomeFragment : Fragment() {

    // ViewModel y Binding
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: vulCondAdapter? = null

    /**
     * Called when the Fragment is created. Inflates the layout defined in FragmentHomeBinding.
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * Called after the view is created. Initializes UI elements, sets up event listeners,
     * and loads vulnerable conditions data.
     *
     * @param view - The root View of the Fragment.
     * @param savedInstanceState - A Bundle containing saved state information.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adaptador vacío
        val arrVulCond = listOf<vulCondItem>()
        adapter = vulCondAdapter(requireActivity(), arrVulCond.toTypedArray())
        binding.rvVulSituations.adapter = adapter

        getVulSituations()
        uploadCustomer()
        setUpListeners()
        scanQR()
        viewModel.getVulSituations()
        getNoticePriv()
        getHelp()
    }

    /**
     * Loads and displays vulnerable conditions data from the ViewModel.
     */
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

    /**
     * Uploads customer registration data to the ViewModel and handles the response.
     */
    private fun uploadCustomer() {
        binding.btnUploadCostumer.setOnClickListener {
            val name = binding.etName.text.toString()
            val pLastName = binding.etPLastName.text.toString()
            val mLastName = binding.etMLastName.text.toString()
            val curp = binding.etCurp.text.toString()
            val bDate = binding.etBDate.text.toString()
            val gender = binding.spGender.selectedItem.toString()
            val vulSituation:Array<String> = getCond()


            if (curp != ""){
                viewModel.uploadCostumer(name, pLastName, mLastName, curp, bDate, gender, vulSituation) {success ->
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
                        }
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Llena los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Sets up event listeners for UI elements.
     */
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

    /**
     * Extracts selected vulnerable conditions from the adapter.
     *
     * @return An array of selected vulnerable conditions.
     */
    private fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }

    /**
     * Resets the registration UI elements, clearing user inputs.
     */
    private fun cleanRegistration() {
        binding.etName.text?.clear()
        binding.etPLastName.text?.clear()
        binding.etMLastName.text?.clear()
        binding.etCurp.text?.clear()
        binding.etBDate.text?.clear()
        binding.spGender.setSelection(0)
        getVulSituations()
    }

    /**
     * Initiates QR code scanning and extracts registration data.
     */
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

    /**
     * Opens a privacy notice link in a web browser.
     */
    private fun getNoticePriv() {
        binding.tvNoticePriv.setOnClickListener {
            val url = "https://platopatodose3.ddns.net:8080/Aviso_de_privacidad.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    /**
     * Opens a help link for CURP (Clave Única de Registro de Población) in a web browser.
     */
    private fun getHelp() {
        binding.tvHelpReg.setOnClickListener {
            val url = "https://www.gob.mx/curp/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}