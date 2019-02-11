package com.david.myweather.weatherList

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.david.myweather.R
import com.david.myweather.data.ResponseDto
import android.arch.lifecycle.Observer
import com.david.myweather.MainActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_weather_list.*
import java.util.ArrayList
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WeatherListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WeatherListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WeatherListFragment : Fragment() {

    @Inject
    lateinit var weatherListViewModelFactory: WeatherListViewModelFactory
    var weatherListAdapter = WeatherListAdapter(ArrayList())
    lateinit var weatherListViewModel: WeatherListViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherListViewModel = ViewModelProviders.of(this, weatherListViewModelFactory)
                .get(WeatherListViewModel::class.java)

        initializeRecycler()
        loadData()

        weatherListViewModel.weatherResult().observe(this,
                Observer<List<ResponseDto>> {
                    if (it != null) {
                        val position = weatherListAdapter.itemCount
                        if (it.isNotEmpty())
                            txt_holder.visibility = View.GONE
                        else
                            txt_holder.visibility = View.VISIBLE
                        weatherListAdapter.aadCityWeather(it)
                        recycler.adapter = weatherListAdapter
                        //recycler.scrollToPosition(position - Constants.LIST_SCROLLING)
                    }
                })

        weatherListViewModel.weatherError().observe(this, Observer<String> {
            if (it != null) {
                (activity as MainActivity).showDialog("An error was encountered")
            }
        })

        weatherListViewModel.weatherLoader().observe(this, Observer<Boolean> {
            (activity as MainActivity).setLoaderState(it!!)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        weatherListViewModel.disposeElements()
    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this.context, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
    }

    fun loadData() {
        weatherListViewModel.loadWeatherInfo()
    }

}
