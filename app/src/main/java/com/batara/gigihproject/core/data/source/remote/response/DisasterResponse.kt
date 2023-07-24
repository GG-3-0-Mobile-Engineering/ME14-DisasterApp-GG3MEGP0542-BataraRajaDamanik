package com.batara.gigihproject.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DisasterResponse(

	@field:SerializedName("result")
	val result: Result,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Objects(

	@field:SerializedName("output")
	val output: Output
)

data class GeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Double>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("properties")
	val properties: Properties
)

data class Output(

	@field:SerializedName("geometries")
	val geometries: List<GeometriesItem>,

	@field:SerializedName("type")
	val type: String
)

data class Result(

	@field:SerializedName("objects")
	val objects: Objects,

	@field:SerializedName("bbox")
	val bbox: List<Double>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("arcs")
	val arcs: List<Double>
)

@Parcelize
data class Tags(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String? = null,

	@field:SerializedName("district_id")
	val districtId: Int? = null,

	@field:SerializedName("local_area_id")
	val localAreaId: Int? = null,

	@field:SerializedName("region_code")
	val regionCode : Int? = null
) : Parcelable

@Parcelize
data class Properties(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("disaster_type")
	val disasterType: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tags")
	val tags: Tags,

	@field:SerializedName("partner_icon")
	val partnerIcon: String? = null,

	@field:SerializedName("pkey")
	val pkey: Int,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("partner_code")
	val partnerCode: String? = null,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class ListCoordinates(
	val coordinates: List<Double>
):Parcelable
