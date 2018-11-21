package ru.neoanon.openweather.view.places

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.databinding.ItemPlaceBinding
import ru.neoanon.openweather.utils.CURRENT_LOCATION_ID

/**
 *Created by eshtefan on  14.11.2018.
 */
typealias PlaceItemClicked = (place: RegionLocation) -> Unit

typealias DeletePlaceItemClicked = (regionId: Long) -> Unit

class PlaceAdapter(val clickListener: PlaceItemClicked, val deleteItemClickListener: DeletePlaceItemClicked) :
    RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {
    private val placesList = arrayListOf<RegionLocation>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemPlaceBinding = DataBindingUtil.inflate(inflater, R.layout.item_place, viewGroup, false)
        return PlaceAdapter.MyViewHolder(binding, this)
    }

    override fun getItemCount(): Int = placesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val regionLocation = placesList[position]
        holder.bind(regionLocation)
        val tvCurrentLocationTitle = holder.binding.tvCurrentLocationTitle
        val ivTrashCan = holder.binding.ivTrashCan
        if (regionLocation.id == CURRENT_LOCATION_ID) {
            tvCurrentLocationTitle.visibility = View.VISIBLE
            ivTrashCan.visibility = View.INVISIBLE
        } else {
            tvCurrentLocationTitle.visibility = View.GONE
            ivTrashCan.visibility = View.VISIBLE
        }
    }

    fun clear() = this.placesList.clear()

    fun add(placesList: List<RegionLocation>) = this.placesList.addAll(placesList)

    class MyViewHolder(val binding: ItemPlaceBinding, adapter: PlaceAdapter) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                adapter.clickListener.invoke(adapter.placesList[adapterPosition])
            }
            binding.ivTrashCan.setOnClickListener {
                adapter.deleteItemClickListener.invoke(adapter.placesList[adapterPosition].id)
            }
        }

        fun bind(regionLocation: RegionLocation) {
            binding.regionLocation = regionLocation
            binding.executePendingBindings()
        }
    }
}