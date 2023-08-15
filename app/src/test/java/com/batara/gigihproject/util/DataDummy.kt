package com.batara.gigihproject.util

import com.batara.gigihproject.core.data.source.local.entity.DisasterEntity
import com.batara.gigihproject.core.data.source.remote.response.GeometriesItem
import com.batara.gigihproject.core.data.source.remote.response.ListCoordinates
import com.batara.gigihproject.core.data.source.remote.response.Properties
import com.batara.gigihproject.core.data.source.remote.response.Tags
import com.batara.gigihproject.core.domain.model.Disaster

object DataDummy {

    fun generateDummyDisaster() : List<Disaster>{
        val disasterList = ArrayList<Disaster>()
        val dummyCoordinates = ListCoordinates(coordinates = listOf(106.6677472407, -6.1203495768))
        val dummyTags = Tags(
            instanceRegionCode = "ID-BT",
            districtId = null,
            localAreaId = null,
            regionCode = 3671

        )
        for (i in 0..10){
            val dummyProperties = Properties(
                imageUrl = "https://images.petabencana.id/b86d0fb5-67b4-41ec-9869-a022ba0efb33.jpg",
                disasterType = "flood",
                createdAt = "2023-08-14T07:09:10.538Z",
                source = "grasp",
                title = "null",
                url = "b86d0fb5-67b4-41ec-9869-a022ba0efb33",
                tags = dummyTags,
                pkey = i,
                text = "Dalam banget",
                partnerCode = null,
                status = "confirmed",
                partnerIcon = null
            )
            val disaster = Disaster(
                pkey = i,
                coordinates = dummyCoordinates,
                type = "Point",
                properties = dummyProperties
            )
            disasterList.add(disaster)
        }
        return disasterList
    }

    fun generateDummyDisasterEntity() : List<DisasterEntity>{
        val disasterList = ArrayList<DisasterEntity>()
        val dummyCoordinates = ListCoordinates(coordinates = listOf(106.6677472407, -6.1203495768))
        val dummyTags = Tags(
            instanceRegionCode = "ID-BT",
            districtId = null,
            localAreaId = null,
            regionCode = 3671

        )
        for (i in 0..10){
            val dummyProperties = Properties(
                imageUrl = "https://images.petabencana.id/b86d0fb5-67b4-41ec-9869-a022ba0efb33.jpg",
                disasterType = "flood",
                createdAt = "2023-08-14T07:09:10.538Z",
                source = "grasp",
                title = "null",
                url = "b86d0fb5-67b4-41ec-9869-a022ba0efb33",
                tags = dummyTags,
                pkey = i,
                text = "Dalam banget",
                partnerCode = null,
                status = "confirmed",
                partnerIcon = null
            )
            val disaster = DisasterEntity(
                pkey = i,
                coordinates = dummyCoordinates,
                type = "Point",
                properties = dummyProperties
            )
            disasterList.add(disaster)
        }
        return disasterList
    }

    fun generateDummyResponseItem() : List<GeometriesItem> {
        val disasterList = ArrayList<GeometriesItem>()
        val dummyCoordinates = listOf(106.6677472407, -6.1203495768)
        val dummyTags = Tags(
            instanceRegionCode = "ID-BT",
            districtId = null,
            localAreaId = null,
            regionCode = 3671

        )
        for (i in 0..10){
            val dummyProperties = Properties(
                imageUrl = "https://images.petabencana.id/b86d0fb5-67b4-41ec-9869-a022ba0efb33.jpg",
                disasterType = "flood",
                createdAt = "2023-08-14T07:09:10.538Z",
                source = "grasp",
                title = "null",
                url = "b86d0fb5-67b4-41ec-9869-a022ba0efb33",
                tags = dummyTags,
                pkey = i,
                text = "Dalam banget",
                partnerCode = null,
                status = "confirmed",
                partnerIcon = null
            )
            val disaster = GeometriesItem(
                coordinates = dummyCoordinates,
                type = "Point",
                properties = dummyProperties
            )
            disasterList.add(disaster)
        }
        return disasterList
    }
}