package com.david.myweather.addCity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.david.myweather.MainActivity
import com.david.myweather.R
import com.david.myweather.data.ResponseDto
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_add_location.view.*
import javax.inject.Inject

/**
 *  Fragment that contains only view-handling related code.
 *
 *  Both fragments share the viewModel to reuse code.
 */
class AddCityFragment : Fragment() {

    val TAG: String = "AddCityFragment"

    @Inject
    lateinit var addCityViewModelFactory: AddCityViewModelFactory
    lateinit var addCityViewModel: AddCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_location, container, false)

        view.btn_add_city.setOnClickListener {
            Log.e(TAG, "clicked")
            if (view!!.edit_city.text.toString().length > 2) {
                (activity as MainActivity).setLoaderState(true)
                addCityViewModel.addCity(view.edit_city.text.toString())
            }
            else
                (activity as MainActivity).showDialog("Please input at least 2 characters")
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addCityViewModel = ViewModelProviders.of(this, addCityViewModelFactory)
            .get(AddCityViewModel::class.java)

        addCityViewModel.citiesResult().observe(this,
            Observer<ResponseDto> {
                if (it != null) {
                    (activity as MainActivity).showDialog(it.name.plus(" added correctly!"))
                }
            })

        addCityViewModel.citiesError().observe(this, Observer<String> {
            if (it != null) {
                (activity as MainActivity).showDialog("Sorry, city not found!")
            }
        })

        addCityViewModel.citiesLoader().observe(this, Observer<Boolean> {
            (activity as MainActivity).setLoaderState(it!!)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        addCityViewModel.disposeElements()
    }
}
