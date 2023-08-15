package com.batara.gigihproject.core.utils

import com.batara.gigihproject.util.DataDummy
import org.junit.Assert.*

import org.junit.Test

class DataMapperTest {


    private val dummyDisaster = DataDummy.generateDummyDisaster()
    private val dummyDisasterEntity = DataDummy.generateDummyDisasterEntity()
    private val dummyDisasterResponse = DataDummy.generateDummyResponseItem()
    @Test
    fun `mapResponsesToEntities maps correctly`() {
        val result = DataMapper.mapResponsesToEntities(dummyDisasterResponse)
        assertEquals(dummyDisasterEntity.size, result.size)
        assertEquals(dummyDisasterEntity.first(), result[0])
    }

    @Test
    fun `mapEntitiesToDomain maps correctly`() {
        val result = DataMapper.mapEntitiesToDomain(dummyDisasterEntity)
        assertEquals(dummyDisaster.size, result.size)
        assertEquals(dummyDisaster.first(), result[0])
    }

    @Test
    fun `mapDomainToEntity maps correctly`() {
        val result = DataMapper.mapDomainToEntity(dummyDisaster.first())
        assertEquals(dummyDisasterEntity.first(), result)
    }
}