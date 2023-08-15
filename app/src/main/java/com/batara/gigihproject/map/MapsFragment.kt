package com.batara.gigihproject.map

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.batara.gigihproject.R
import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.model.FilterLocation
import com.batara.gigihproject.core.ui.DisasterAdapter
import com.batara.gigihproject.core.ui.FilterLocationDisasterAdapter
import com.batara.gigihproject.core.utils.RecyclerViewClickListener
import com.batara.gigihproject.core.utils.Utils
import com.batara.gigihproject.databinding.FragmentMapsBinding
import com.batara.gigihproject.setting.SettingActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), RecyclerViewClickListener {

    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val mapViewModel : MapViewModel by viewModels()

    private val listFilter = ArrayList<String>()
    private val listFilterLocation = ArrayList<FilterLocation>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initBottomSheet()
    }

    private fun init() {
        val disasterAdapter = DisasterAdapter()
        val locationDisasterAdapter = FilterLocationDisasterAdapter(disasterAdapter)

        with(binding) {
            disasterRv.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = disasterAdapter
            }

            rvListLocation.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = locationDisasterAdapter
            }

            sflListLocation.setOnRefreshListener {
                showRecyclerList(locationDisasterAdapter)
                sflListLocation.isRefreshing = false
            }
        }

        initData(disasterAdapter, locationDisasterAdapter)
        loadData(disasterAdapter)
        initSearchView(locationDisasterAdapter)
        initFabDate(disasterAdapter)
    }

    private fun initBottomSheet() {
        val standardBottomSheet = activity?.findViewById<FrameLayout>(R.id.bottomSheet)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet ?: requireView())
        standardBottomSheetBehavior.peekHeight = 400
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.isHideable = false
    }

    private fun initSearchView(locationDisasterAdapter : FilterLocationDisasterAdapter) {
        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun afterTextChanged(p0: Editable?) {
                        val query = p0.toString()
                        locationDisasterAdapter.filter(query)
                    }
                })
            searchBar.inflateMenu(R.menu.menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.menuSetting -> {
                        val intent = Intent(activity, SettingActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()
                        true
                    } else -> {
                    false
                }
                }
            }
        }
    }


    private fun initFabDate(disasterAdapter: DisasterAdapter) {
        binding.fabDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .build()
            activity?.supportFragmentManager?.let { it1 -> datePicker.show(it1, "DatePicker") }

            datePicker.addOnPositiveButtonClickListener {selection ->
                val value = Utils.encodeDate(selection.first, selection.second)
                filterDate(disasterAdapter,value)

            }

            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(activity?.applicationContext, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
            }

            datePicker.addOnCancelListener {
                Toast.makeText(activity?.applicationContext, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initData(disasterAdapter: DisasterAdapter, locationDisasterAdapter: FilterLocationDisasterAdapter) {
        listFilter.addAll(listFilterDisaster)
        addChipDisaster(listFilter, disasterAdapter)

        listFilterLocation.addAll(listFilterLocationDisaster)
        showRecyclerList(locationDisasterAdapter)
    }

    private fun addChipDisaster(listFilter: ArrayList<String>, disasterAdapter: DisasterAdapter) {
        for (i in listFilter.indices) {
            val chip = Chip(context)
            chip.text = listFilter[i]
            chip.isChipIconVisible = true
            binding.chipGroupDisaster.addView(chip)
            chip.setOnClickListener {
                Toast.makeText(context, listFilter[i], Toast.LENGTH_SHORT).show()
                loadDataFilter(disasterAdapter, listFilter[i].lowercase())
            }
        }
    }


    private fun filterDate(disasterAdapter: DisasterAdapter, value: List<String>) {
        mapViewModel.updateDisasterFilterDate(value)
        mapViewModel.filteredDisastersDate.observe(viewLifecycleOwner) { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> showLoadingState()

                    is Resource.Success -> showSuccessState(disasterAdapter, disaster)

                    is Resource.Error -> showErrorState(disaster)
                }
            }
        }
    }

    private fun loadData(disasterAdapter: DisasterAdapter) {
        mapViewModel.disaster.observe(viewLifecycleOwner) { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> showLoadingState()

                    is Resource.Success -> showSuccessState(disasterAdapter, disaster)

                    is Resource.Error -> showErrorState(disaster)
                }
            }
        }
    }

    private fun loadDataFilter(disasterAdapter: DisasterAdapter, filter : String) {
        mapViewModel.updateDisasterFilter(filter)
        mapViewModel.filteredDisasters.observe(viewLifecycleOwner) { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> showLoadingState()

                    is Resource.Success -> showSuccessState(disasterAdapter, disaster)

                    is Resource.Error -> showErrorState(disaster)
                }
            }
        }
    }

    private fun loadDataFilterLocation(disasterAdapter: DisasterAdapter, filter : String) {
        mapViewModel.updateDisasterFilterLocation(filter)
        mapViewModel.filteredDisastersLocation.observe(viewLifecycleOwner) { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> showLoadingState()

                    is Resource.Success -> showSuccessState(disasterAdapter, disaster)

                    is Resource.Error -> showErrorState(disaster)
                }
            }
        }
    }


    private fun showErrorState(disaster: Resource.Error<List<Disaster>>) {
        binding.progressBar.visibility = View.GONE
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.viewError.root.visibility = View.VISIBLE
        binding.viewError.tvError.text =
            disaster.massage ?: getString(R.string.errorText)
    }


    private fun showSuccessState(
        disasterAdapter: DisasterAdapter,
        disaster: Resource.Success<List<Disaster>>
    ) {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        disasterAdapter.setData(disaster.data)
        val callback = OnMapReadyCallback { googleMap ->
            if (disaster.data != null) {
                googleMap.clear()
                for (i in disaster.data.indices) {
                    googleMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                disaster.data[i].coordinates.coordinates[1],
                                disaster.data[i].coordinates.coordinates[0]
                            )
                        ).title(disaster.data[i].type)
                    )
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                disaster.data[i].coordinates.coordinates[1],
                                disaster.data[i].coordinates.coordinates[0]
                            )
                        )
                    )
                }
            }
        }
        showLocation(callback)
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private val listFilterDisaster: ArrayList<String>
        get() {
            val dataType = resources.getStringArray(R.array.filter_disaster)
            val list = ArrayList<String>()
            for (i in dataType.indices) {
                list.add(dataType[i])
            }
            return list
        }

    private val listFilterLocationDisaster: ArrayList<FilterLocation>
        get() {
            val dataType = resources.getStringArray(R.array.filter_location)
            val dataDesc = resources.getStringArray(R.array.data_filter_location)
            val list = ArrayList<FilterLocation>()
            for (i in dataType.indices) {
                val filter = FilterLocation(dataType[i], dataDesc[i])
                list.add(filter)
            }
            return list
        }

    private fun showRecyclerList(filterDisasterAdapter: FilterLocationDisasterAdapter) {
        filterDisasterAdapter.listener = this
        filterDisasterAdapter.setData(listFilterLocationDisaster)
    }

    private fun showLocation(callback: OnMapReadyCallback) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.progressBar.visibility = View.GONE
    }

    override fun onItemClicked(view: View, data: FilterLocation, disasterAdapter: DisasterAdapter) {
        with(binding){
            searchBar.text = data.type
            searchView.hide()
        }
        loadDataFilterLocation(disasterAdapter, data.value)
        Toast.makeText(activity, data.type, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
