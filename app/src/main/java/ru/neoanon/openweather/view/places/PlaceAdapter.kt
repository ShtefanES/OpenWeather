package ru.neoanon.openweather.view.places

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_place.view.*
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.utils.CURRENT_LOCATION_ID

/**
 *Created by eshtefan on  14.11.2018.
 */
typealias PlaceItemClicked = (place: RegionLocation) -> Unit

typealias DeletePlaceItemClicked = (regionId: Long) -> Unit

class PlaceAdapter(val clickListener: PlaceItemClicked, val deleteItemClickListener: DeletePlaceItemClicked) :
    RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {
    private val placesList = arrayListOf<RegionLocation>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder =
        PlaceAdapter.MyViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_place, viewGroup, false),
            this
        )

    override fun getItemCount(): Int = placesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val regionLocation = placesList[position]
        holder.root.tv_location_name.text = regionLocation.name
        val tvCurrentLocationTitle = holder.root.tv_current_location_title
        val ivTrashCan = holder.root.iv_trash_can
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

    class MyViewHolder(val root: View, adapter: PlaceAdapter) : RecyclerView.ViewHolder(root) {
        init {
            root.setOnClickListener {
                adapter.clickListener.invoke(adapter.placesList[adapterPosition])
            }
            root.iv_trash_can.setOnClickListener {
                adapter.deleteItemClickListener.invoke(adapter.placesList[adapterPosition].id)
            }
        }
    }
}