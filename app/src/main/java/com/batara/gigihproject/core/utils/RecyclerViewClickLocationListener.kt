package com.batara.gigihproject.core.utils

import android.view.View
import com.batara.gigihproject.core.domain.model.FilterLocation
import com.batara.gigihproject.core.ui.DisasterAdapter

interface RecyclerViewClickLocationListener {
    fun onItemClickedLocation(view: View, data: FilterLocation, disasterAdapter: DisasterAdapter)
}