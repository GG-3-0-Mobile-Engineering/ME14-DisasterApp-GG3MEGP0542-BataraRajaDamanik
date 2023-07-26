package com.batara.gigihproject.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterDisaster(
    val type : String,
    val value : String,
) : Parcelable
