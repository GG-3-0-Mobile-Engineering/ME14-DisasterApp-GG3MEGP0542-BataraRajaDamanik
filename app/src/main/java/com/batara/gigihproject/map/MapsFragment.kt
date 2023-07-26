package com.batara.gigihproject.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.batara.gigihproject.R
import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.FilterDisaster
import com.batara.gigihproject.core.domain.model.FilterLocation
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import com.batara.gigihproject.core.ui.DisasterAdapter
import com.batara.gigihproject.core.ui.FilterDisasterAdapter
import com.batara.gigihproject.core.ui.FilterLocationDisasterAdapter
import com.batara.gigihproject.core.utils.RecyclerViewClickListener
import com.batara.gigihproject.core.utils.RecyclerViewClickLocationListener
import com.batara.gigihproject.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MapsFragment : Fragment(), RecyclerViewClickListener, RecyclerViewClickLocationListener {

    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val disasterUseCase: DisasterUseCase by inject()
    private val mapViewModel : MapViewModel by viewModel{parametersOf(disasterUseCase)}

    private val listFilter = ArrayList<FilterDisaster>()
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
        val disasterAdapter = DisasterAdapter()

        val filterDisasterAdapter = FilterDisasterAdapter(disasterAdapter)
        filterDisasterAdapter.listener = this

        val filterLocationDisasterAdapter = FilterLocationDisasterAdapter(disasterAdapter)
        filterLocationDisasterAdapter.listener = this

        with(binding.disasterRv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = disasterAdapter
        }

        with(binding.rvFilterDisaster) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = filterDisasterAdapter
        }

        with(binding.rvFilterLocationDisaster) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = filterLocationDisasterAdapter
        }

        loadData(disasterAdapter)

        listFilter.addAll(listFilterDisaster)
        showRecyclerList(filterDisasterAdapter)

        listFilterLocation.addAll(listFilterLocationDisaster)
        showRecyclerListLocation(filterLocationDisasterAdapter)

        val standardBottomSheet = activity?.findViewById<FrameLayout>(R.id.bottomSheet)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet ?: requireView())
        standardBottomSheetBehavior.peekHeight = 400
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.isHideable = false

        standardBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.dragHandle.visibility = View.GONE
                    binding.btExpand.visibility = View.VISIBLE
                    binding.tvExpand.visibility = View.VISIBLE
                } else {
                    binding.dragHandle.visibility = View.VISIBLE
                    binding.btExpand.visibility = View.GONE
                    binding.tvExpand.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        binding.btExpand.setOnClickListener() {
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.tilSearch.setEndIconOnClickListener {
            Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(disasterAdapter: DisasterAdapter) {
        mapViewModel.disaster.observe(viewLifecycleOwner, { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        disasterAdapter.setData(disaster.data)
                        val callback = OnMapReadyCallback { googleMap ->
                            if (disaster.data != null){
                                for (i in disaster.data.indices){
                                    googleMap.addMarker(MarkerOptions().position(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])).title(disaster.data[i].type))
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])))
                                }
                            }
                        }
                        showLocation(callback)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = disaster.massage ?: getString(R.string.errorText)
                    }
                }
            }
        })
    }

    private val listFilterDisaster: ArrayList<FilterDisaster>
        get() {
            val dataType = resources.getStringArray(R.array.filter_disaster)
            val dataDesc = resources.getStringArray(R.array.data_filter_disaster)
            val list = ArrayList<FilterDisaster>()
            for (i in dataType.indices) {
                val filter = FilterDisaster(dataType[i], dataDesc[i])
                list.add(filter)
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

    private fun showRecyclerList(filterDisasterAdapter: FilterDisasterAdapter) {
        filterDisasterAdapter.setData(listFilter)
    }

    private fun showRecyclerListLocation(filterDisasterAdapter: FilterLocationDisasterAdapter) {
        filterDisasterAdapter.setData(listFilterLocation)
    }

    private fun showLocation(callback: OnMapReadyCallback) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(view: View, data: FilterDisaster, disasterAdapter: DisasterAdapter) {
        loadDataFilter(disasterAdapter, data.value)
        Toast.makeText(activity, data.value, Toast.LENGTH_SHORT).show()
    }

    private fun loadDataFilter(disasterAdapter: DisasterAdapter, filter : String) {
        mapViewModel.updateDisasterFilter(filter)
        mapViewModel.filteredDisasters.observe(viewLifecycleOwner, { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        disasterAdapter.setData(disaster.data)
                        val callback = OnMapReadyCallback { googleMap ->
                            googleMap.clear()
                            if (disaster.data != null){
                                for (i in disaster.data.indices){
                                    googleMap.addMarker(MarkerOptions().position(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])).title(disaster.data[i].type))
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])))
                                }
                            }
                        }
                        showLocation(callback)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = disaster.massage ?: getString(R.string.errorText)
                    }
                }
            }
        })
    }

    private fun loadDataFilterLocation(disasterAdapter: DisasterAdapter, filter : String) {
        mapViewModel.updateDisasterFilter(filter)
        mapViewModel.filteredDisastersLocation.observe(viewLifecycleOwner, { disaster ->
            if (disaster != null) {
                when (disaster) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        disasterAdapter.setData(disaster.data)
                        val callback = OnMapReadyCallback { googleMap ->
                            googleMap.clear()
                            if (disaster.data != null){
                                for (i in disaster.data.indices){
                                    googleMap.addMarker(MarkerOptions().position(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])).title(disaster.data[i].type))
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(disaster.data[i].coordinates.coordinates[1],
                                        disaster.data[i].coordinates.coordinates[0])))
                                }
                            }
                        }
                        showLocation(callback)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = disaster.massage ?: getString(R.string.errorText)
                    }
                }
            }
        })
    }

    override fun onItemClickedLocation(
        view: View,
        data: FilterLocation,
        disasterAdapter: DisasterAdapter
    ) {
        loadDataFilterLocation(disasterAdapter, data.value)
        Toast.makeText(activity, data.value, Toast.LENGTH_SHORT).show()
    }

    companion object {

    }
}