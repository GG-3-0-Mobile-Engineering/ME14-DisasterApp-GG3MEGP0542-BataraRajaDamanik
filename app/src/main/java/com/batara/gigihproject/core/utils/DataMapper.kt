package com.batara.gigihproject.core.utils

import com.batara.gigihproject.core.data.source.local.entity.DisasterEntity
import com.batara.gigihproject.core.data.source.remote.response.GeometriesItem
import com.batara.gigihproject.core.data.source.remote.response.ListCoordinates
import com.batara.gigihproject.core.domain.model.Disaster

object DataMapper {
    fun mapResponsesToEntities(input: List<GeometriesItem>): List<DisasterEntity> {
        val disasterList = ArrayList<DisasterEntity>()
        input.map {
            val disaster = DisasterEntity(
                pkey = it.properties.pkey,
                coordinates = ListCoordinates(it.coordinates),
                type = it.type,
                properties = it.properties
            )
            disasterList.add(disaster)
        }
        return disasterList
    }

    fun mapEntitiesToDomain(input: List<DisasterEntity>): List<Disaster> =
        input.map {
            Disaster(
                pkey = it.pkey,
                coordinates = it.coordinates,
                type = it.type,
                properties = it.properties,
            )
        }

    fun mapDomainToEntity(input: Disaster) = DisasterEntity(
        pkey = input.pkey,
        coordinates = input.coordinates,
        type = input.type,
        properties = input.properties,
    )
}