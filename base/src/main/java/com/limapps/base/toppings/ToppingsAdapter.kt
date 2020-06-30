package com.limapps.base.toppings

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.limapps.base.R
import com.limapps.base.models.Topping
import com.limapps.base.models.ToppingCategory
import com.limapps.base.utils.priceToString
import kotlin.properties.Delegates

open class ToppingsAdapter(private val listener: ToppingCategoryListener)
    : RecyclerView.Adapter<ToppingsAdapter.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_INCLUSIVE = 0
        const val VIEW_TYPE_EXCLUSIVE = 1
    }

    protected var toppings: MutableList<Topping> = ArrayList()
    protected lateinit var type: String
    private var minToppings: Int by Delegates.notNull()
    protected var maxToppings: Int by Delegates.notNull()
    private var maxToppingsReached: Boolean by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_EXCLUSIVE -> ViewHolder(inflater.inflate(R.layout.item_topping_single, parent, false))
            else -> ViewHolder(inflater.inflate(R.layout.item_topping_multiple, parent, false))
        }
    }

    override fun onBindViewHolder(lanesHolder: ViewHolder, position: Int) {
        val topping = toppings[position]

        lanesHolder.textViewToppingName.text = topping.description
        when {
            topping.price == 0.0 -> lanesHolder.textViewToppingPrice.visibility = View.GONE
            topping.price > 0 -> {
                val priceFormatted = lanesHolder.textViewToppingPrice.context
                        .getString(R.string.copy_code_country, topping.price.priceToString())
                lanesHolder.textViewToppingPrice.text = priceFormatted
                lanesHolder.textViewToppingPrice.visibility = View.VISIBLE
            }
            else -> {
                val priceFormatted = topping.price.priceToString()
                lanesHolder.textViewToppingPrice.text = priceFormatted
                lanesHolder.textViewToppingPrice.visibility = View.VISIBLE
            }
        }
        val compoundButton = lanesHolder.compoundButton
        compoundButton.setOnCheckedChangeListener(null)
        if (type == ToppingCategory.INCLUSIVE) {
            compoundButton.isEnabled = maxToppingsReached && topping.isChecked || !maxToppingsReached
        }
        compoundButton.isChecked = topping.isChecked
        lanesHolder.layoutToppingContainer.setOnClickListener {
            changeButtonState(lanesHolder.compoundButton)
        }

        compoundButton.setOnCheckedChangeListener { _, isChecked ->
            handleSelectionChange(topping, isChecked)
        }
    }

    private fun handleSelectionChange(topping: Topping, isChecked: Boolean) {
        listener.resetInvalidCategoryAnimation()

        if (type == ToppingCategory.EXCLUSIVE) {
            val mappedToppings = toppings.map {
                val currentQuantity = when (it) {
                    topping -> 1
                    else -> 0
                }
                it.copy(isChecked = if (it == topping) !it.isChecked else false, units = currentQuantity)
            }
            listener.updatePriceWithToppings(mappedToppings.toMutableList())
        } else {
            when (isChecked) {
                true -> validateNewCheckedTopping(topping)
                false -> validateUncheckedTopping(topping)
            }
        }
    }

    private fun validateNewCheckedTopping(topping: Topping) {
        val nextCheckedQuantity = toppingsCheckedQuantity().inc()
        if (nextCheckedQuantity <= maxToppings) {
            val mappedToppings = toppings.map {
                it as Topping
                val currentQuantity = when (it) {
                    topping -> 1
                    else -> it.units
                }
                it.copy(isChecked = if (it == topping) true else it.isChecked, units = currentQuantity)
            }
            listener.updatePriceWithToppings(mappedToppings.toMutableList())
            maxToppingsReached = nextCheckedQuantity == maxToppings
        }
    }

    private fun validateUncheckedTopping(topping: Topping) {
        val mappedToppings = toppings.map {
            it as Topping
            val currentQuantity = when (it) {
                topping -> 0
                else -> it.units
            }
            it.copy(isChecked = if (it == topping) false else it.isChecked, units = currentQuantity)
        }
        if (maxToppingsReached) {
            maxToppingsReached = false
        }
        listener.updatePriceWithToppings(mappedToppings.toMutableList())
    }

    fun toppingsCheckedQuantity(): Int = toppings.filter { it.isChecked }.sumBy { it.units ?: 1 }

    private fun changeButtonState(compoundButton: CompoundButton) {
        compoundButton.isChecked = !compoundButton.isChecked
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = toppings.size

    override fun getItemViewType(position: Int): Int = when (type) {
        ToppingCategory.INCLUSIVE -> VIEW_TYPE_INCLUSIVE
        ToppingCategory.EXCLUSIVE -> VIEW_TYPE_EXCLUSIVE
        else -> -1
    }

    fun setItems(toppings: List<Topping>, type: String, minToppings: Int, maxToppings: Int) {
        this.toppings = ArrayList()
        this.toppings.addAll(toppings.sortedBy { it.index })
        this.maxToppingsReached = toppingsCheckedQuantity() == maxToppings
        this.type = type
        this.minToppings = minToppings
        this.maxToppings = maxToppings
    }

    open class ViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        internal val layoutToppingContainer by lazy { v.findViewById<ConstraintLayout>(R.id.layout_topping_container) }
        internal val textViewToppingName by lazy { v.findViewById<TextView>(R.id.textView_topping_name) }
        internal val textViewToppingPrice by lazy { v.findViewById<TextView>(R.id.textView_topping_price) }
        internal val compoundButton by lazy { v.findViewById<CompoundButton>(R.id.checkbox_topping) }
    }

    interface ToppingCategoryListener {
        fun resetInvalidCategoryAnimation()
        fun updatePriceWithToppings(toppings: MutableList<Topping>)
    }
}
