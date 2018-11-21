package ru.neoanon.openweather.view.places

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.databinding.ItemSuggestionBinding

/**
 *Created by eshtefan on  14.11.2018.
 */

typealias SuggestionItemClicked = (item: RegionLocation) -> Unit

class SuggestionsAdapter(val clickListener: SuggestionItemClicked) :
    RecyclerView.Adapter<SuggestionsAdapter.MyViewHolder>() {
    private val suggestionList = arrayListOf<RegionLocation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemSuggestionBinding = DataBindingUtil.inflate(inflater, R.layout.item_suggestion, parent, false)
        return SuggestionsAdapter.MyViewHolder(binding, this)
    }

    override fun getItemCount(): Int = suggestionList.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val regionLocation = suggestionList[p1]
        p0.bind(regionLocation)
    }

    fun clear() = this.suggestionList.clear()

    fun add(suggestionList: List<RegionLocation>) = this.suggestionList.addAll(suggestionList)

    class MyViewHolder(val binding: ItemSuggestionBinding, adapter: SuggestionsAdapter) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { adapter.clickListener.invoke(adapter.suggestionList[adapterPosition]) }
        }

        fun bind(regionLocation: RegionLocation) {
            binding.regionLocation = regionLocation
            binding.executePendingBindings()
        }
    }
}