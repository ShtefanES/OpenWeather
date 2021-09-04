package ru.neoanon.openweather.view.places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_suggestion.view.*
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation

/**
 *Created by eshtefan on  14.11.2018.
 */

typealias SuggestionItemClicked = (item: RegionLocation) -> Unit

class SuggestionsAdapter(val clickListener: SuggestionItemClicked) :
    RecyclerView.Adapter<SuggestionsAdapter.MyViewHolder>() {
    private val suggestionList = arrayListOf<RegionLocation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        SuggestionsAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_suggestion,
                parent,
                false
            ), this
        )

    override fun getItemCount(): Int = suggestionList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val regionLocation = suggestionList[position]
        holder.root.tv_location_name.text = regionLocation.name
    }

    fun clear() = this.suggestionList.clear()

    fun add(suggestionList: List<RegionLocation>) = this.suggestionList.addAll(suggestionList)

    class MyViewHolder(val root: View, adapter: SuggestionsAdapter) :
        RecyclerView.ViewHolder(root) {
        init {
            root.setOnClickListener { adapter.clickListener.invoke(adapter.suggestionList[adapterPosition]) }
        }
    }
}