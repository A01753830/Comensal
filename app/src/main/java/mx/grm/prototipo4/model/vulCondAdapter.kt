package mx.grm.prototipo4.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import mx.grm.prototipo4.R
import mx.grm.prototipo4.databinding.VulSituationBinding
import mx.grm.prototipo4.model.responses.vulCondItem

/**
 * Vulnerable Condition Adapter
 *
 * This adapter class is responsible for populating a RecyclerView with items representing
 * vulnerable conditions for a customer. It allows users to select and deselect conditions
 * using checkboxes and provides a list of selected conditions.
 *
 * @param context The context in which the adapter is used.
 * @param arrVulCond An array of vulnerable condition items to be displayed.
 *
 * @property selectedConditions A list of selected vulnerable conditions.
 *
 * @constructor Creates a `vulCondAdapter` with the given context and an array of vulnerable condition items.
 * @sample Context
 * @sample Array
 *
 * @see RecyclerView.Adapter
 *
 * @return Unit
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class vulCondAdapter(private val context: Context, var arrVulCond: Array<vulCondItem>)
    : RecyclerView.Adapter<vulCondAdapter.vulCondCheck>()
{
    /**
     * List of selected vulnerable conditions
     */
    var selectedConditions: MutableList<String> = mutableListOf()

    /**
     * Vulnerable Condition ViewHolder
     *
     * Represents a view holder for a vulnerable condition item.
     *
     * @param checkBoxView The view containing the vulnerable condition checkbox.
     *
     * @constructor Creates a `vulCondCheck` with the provided view.
     * @sample View
     *
     * @return Unit
     */
    class vulCondCheck(var checkBoxView: View): RecyclerView.ViewHolder(checkBoxView)
    {
        /**
         * Set the condition name for the checkbox.
         *
         * @param conditionName The vulnerable condition item to set the name.
         */
        fun set(conditionName: vulCondItem) {
            checkBoxView.findViewById<CheckBox>(R.id.cb_vulCondition).text = conditionName.Nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vulCondCheck {
        val binding = VulSituationBinding.inflate(LayoutInflater.from(context))
        return vulCondCheck(binding.root)
    }

    override fun getItemCount(): Int {
        return arrVulCond.size
    }

    override fun onBindViewHolder(holder: vulCondCheck, position: Int) {
        val conditionName = arrVulCond[position]
        holder.set(conditionName)

        // Handle check/uncheck from checkbox
        holder.checkBoxView.findViewById<CheckBox>(R.id.cb_vulCondition)
            .setOnCheckedChangeListener{_, isChecked ->
                if(isChecked) {
                    selectedConditions.add(conditionName.Nombre)
                    println("Condiciones: $selectedConditions")
                } else {
                    selectedConditions.remove(conditionName.Nombre)
                    println("Condiciones: $selectedConditions")
                }
            }
    }

}