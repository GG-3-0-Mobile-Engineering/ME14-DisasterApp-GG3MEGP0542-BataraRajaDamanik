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
import com.batara.gigihproject.core.ui.DisasterAdapter
import com.batara.gigihproject.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment() {

    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel : MapViewModel by viewModel()

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

        with(binding.disasterRv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = disasterAdapter
        }

//        with(binding){
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { textView, actionId, event ->
//                    searchBar.text = searchView.text
//                    searchView.hide()
//                    false
//                }
//
//            searchBar.inflateMenu(R.menu.menu)
//            searchBar.setOnMenuItemClickListener { menuItem ->
//                when(menuItem.itemId) {
//                    R.id.menu1 -> {
//                        true
//                    } else -> false
//                }
//            }
//        }


        val standardBottomSheet = activity?.findViewById<FrameLayout>(R.id.bottomSheet)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet ?: requireView())
        standardBottomSheetBehavior.peekHeight = 400
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
            Toast.makeText(activity, "Hola", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLocation(callback: OnMapReadyCallback) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}