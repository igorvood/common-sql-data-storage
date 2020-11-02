package ru.vood.commosqldatastorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommoSqlDataStorageApplication

fun main(args: Array<String>) {
    runApplication<CommoSqlDataStorageApplication>(*args)
}
