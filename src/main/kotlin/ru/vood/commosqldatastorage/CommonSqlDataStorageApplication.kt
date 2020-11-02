package ru.vood.commosqldatastorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommonSqlDataStorageApplication

fun main(args: Array<String>) {
    runApplication<CommonSqlDataStorageApplication>(*args)
}
