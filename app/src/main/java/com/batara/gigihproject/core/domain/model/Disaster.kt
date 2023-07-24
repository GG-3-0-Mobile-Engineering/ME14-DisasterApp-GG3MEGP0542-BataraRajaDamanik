package com.batara.gigihproject.core.domain.model

import android.os.Parcelable
import com.batara.gigihproject.core.data.source.remote.response.ListCoordinates
import com.batara.gigihproject.core.data.source.remote.response.Properties
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disaster (
    val pkey : Int,
    val coordinates: ListCoordinates,
    val type: String,
    val properties: Properties
) : Parcelable