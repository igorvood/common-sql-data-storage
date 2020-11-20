package ru.vood.commosqldatastorage.meta

import java.util.*

interface OracleMetaDao {

    fun getAllMeta(): TreeMap<String, TableMeta>

}