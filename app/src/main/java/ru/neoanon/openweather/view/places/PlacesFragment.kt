package ru.neoanon.openweather.view.places

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import ru.neoanon.openweather.R
import ru.neoanon.openweather.app.App
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.databinding.FragmentPlacesBinding
import ru.neoanon.openweather.utils.clickView
import ru.neoanon.openweather.utils.searchViewCloseListener
import ru.neoanon.openweather.utils.searchViewFocusChangeListener
import ru.neoanon.openweather.view.mainscreen.WeatherViewModel
import ru.neoanon.openweather.view.mainscreen.WeatherViewModelFactory
import javax.inject.Inject

/**
 *Created by eshtefan on  14.11.2018.
 */

class PlacesFragment : Fragment() {
    private lateinit var ivArrowBack: ImageView
    private lateinit var placesRecyclerView: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var searchView: SearchView
    private lateinit var suggestionRecyclerView: RecyclerView
    private lateinit var suggestionsAdapter: SuggestionsAdapter
    private lateinit var placeAdapter: PlaceAdapter
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory
    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPlacesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_places, container, false
        )
        searchView = binding.searchView
        tvTitle = binding.tvNavigationTitle
        ivArrowBack = binding.ivNavigationArrowBack
        suggestionRecyclerView = binding.suggestionRecyclerView
        suggestionsAdapter = SuggestionsAdapter { item: RegionLocation ->
            minimizingSearchIcon()
            weatherViewModel.closeDrawer()
            disposable.add(
                weatherViewModel.selectRegionLocation(item)
                    .subscribe({}, {})
            )
        }
        suggestionRecyclerView.setAdapter(suggestionsAdapter)
        placesRecyclerView = binding.placesRecyclerView
        placeAdapter = PlaceAdapter({ place: RegionLocation ->
            minimizingSearchIcon()
            weatherViewModel.closeDrawer()
            disposable.add(
                weatherViewModel.selectRegionLocation(place)
                    .subscribe({}, {})
            )
        }, { regionId ->
            disposable.add(
                weatherViewModel.deleteRegionLocation(regionId)
                    .subscribe({}, {})
            )
        })
        placesRecyclerView.adapter = placeAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as App).getAppComponent().inject(this)

        weatherViewModel = ViewModelProviders.of(activity!!, weatherViewModelFactory).get(WeatherViewModel::class.java)

        locationViewModel =
                ViewModelProviders.of(activity!!, locationViewModelFactory).get(LocationViewModel::class.java)
    }

    override fun onStart() {
        super.onStart();

        setSearchViewListeners(searchView)

        disposable.add(
            locationViewModel.isTitleVisible
                .subscribe { aBoolean -> tvTitle.visibility = if (aBoolean) View.VISIBLE else View.GONE })

        disposable.add(
            locationViewModel.isSuggestionVisible
                .subscribe { aBoolean ->
                    suggestionRecyclerView.visibility = if (aBoolean) View.VISIBLE else View.GONE
                })

        disposable.add(
            locationViewModel.isPlacesVisible
                .subscribe { aBoolean -> placesRecyclerView.visibility = if (aBoolean) View.VISIBLE else View.GONE })

        disposable.add(
            LocationViewModel.clickView(ivArrowBack)
                .subscribe {
                    if (!searchView.isIconified()) {
                        minimizingSearchIcon()
                    } else {
                        weatherViewModel.closeDrawer()
                    }
                })

        disposable.add(
            weatherViewModel.subscribeRegionLocations()
                .subscribe({ regionLocations ->
                    placeAdapter.clear()
                    placeAdapter.add(regionLocations)
                    placeAdapter.notifyDataSetChanged()
                }, {})
        )
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun setSearchViewListeners(searchView: SearchView) {
        disposable.add(
            locationViewModel.addressSuggestions(searchView)
                .subscribe({ regionLocations ->
                    suggestionsAdapter.clear()
                    suggestionsAdapter.add(regionLocations)
                    suggestionsAdapter.notifyDataSetChanged()
                }, {})
        )
        disposable.add(LocationViewModel.searchViewCloseListener(searchView)
            .subscribe { locationViewModel.setTitleVisible(true) })

        disposable.add(LocationViewModel.searchViewFocusChangeListener(searchView)
            .subscribe { aBoolean ->
                if (aBoolean) {
                    locationViewModel.setTitleVisible(false)
                }
            })
    }

    private fun minimizingSearchIcon() {
        searchView.setIconified(true)//twice call because if searchView has text then your call of setIconified(true) just clear text without minimizing the icon
        searchView.setIconified(true)
        locationViewModel.setTitleVisible(true)
    }
}