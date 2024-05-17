package com.example.locmart.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.data.api.product.dto.Section
import com.example.locmart.data.api.product.dto.SectionType
import com.example.locmart.databinding.ItemSectionsHorizontalBinding
import com.example.locmart.databinding.ItemSectionsVerticalBinding


class SectionAdapter(
    private val sections: List<Section>,
    private val showAll: (section: Section) -> Unit,
    private val onClick: (product: Product) -> Unit,
    private val like: (product: Product) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class HarizontalHolder(private val binding: ItemSectionsHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) = with(binding) {
            title.text = section.title
            showAll.setOnClickListener {
                this@SectionAdapter.showAll(section)
            }
            products.adapter = HarizontalAdapter(section.products,onClick,like)
        }
    }

    inner class VerticalHolder(private val binding: ItemSectionsVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) = with(binding) {
            title.text = section.title
            showAll.setOnClickListener {
                this@SectionAdapter.showAll(section)
            }
            products.adapter = VerticalAdapter(section.products,onClick,like)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return when (SectionType.values()[viewType]) {

            SectionType.horizontal -> HarizontalHolder(
                ItemSectionsHorizontalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            SectionType.vertical -> VerticalHolder(
                ItemSectionsVerticalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HarizontalHolder -> holder.bind(sections[position])
            is VerticalHolder -> holder.bind(sections[position])
        }
    }

    override fun getItemCount() = sections.size

    override fun getItemViewType(position: Int) = sections[position].type.ordinal


//    companion object {
//        private const val HORIZONTAL = 0
//        private const val VERTICAL = 1
//    }

}