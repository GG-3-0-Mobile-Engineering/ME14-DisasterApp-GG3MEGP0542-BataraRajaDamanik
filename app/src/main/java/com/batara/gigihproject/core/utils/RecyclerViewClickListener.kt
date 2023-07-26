package com.batara.gigihproject.core.utils

import android.view.View
import com.batara.gigihproject.core.domain.model.FilterDisaster
import com.batara.gigihproject.core.ui.DisasterAdapter
import com.batara.gigihproject.core.ui.FilterDisasterAdapter

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, data: FilterDisaster, disasterAdapter: DisasterAdapter)
}