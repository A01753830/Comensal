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
 * Adapter for the Customer's Vulnerable Situation
 * @author Héctor González Sánchez
 */

class vulCondAdapter(private val context: Context, var arrVulCond: Array<vulCondItem>)
    : RecyclerView.Adapter<vulCondAdapter.vulCondCheck>()
{
    var selectedConditions: MutableList<String> = mutableListOf()
    class vulCondCheck(var checkBoxView: View): RecyclerView.ViewHolder(checkBoxView)
    {
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

        // Handle check/uncheck from checkbox (New)
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