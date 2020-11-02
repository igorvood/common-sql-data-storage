package ru.vood.commosqldatastorage.meta

interface OracleMetaDao {

    fun getAllMeta(): Map<String, TableMeta>

}