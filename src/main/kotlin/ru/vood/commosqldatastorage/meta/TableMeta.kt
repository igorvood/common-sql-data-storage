package ru.vood.commosqldatastorage.meta


data class TableMeta(
        val columns: MutableMap<String, TableColumn>,
        val uks: MutableMap<String, Unique>,
)
