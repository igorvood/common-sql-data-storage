package ru.vood.commosqldatastorage.delete

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.commosqldatastorage.meta.OracleMetaDao

@Service
class Runner(val oracleMetaDao: OracleMetaDao):CommandLineRunner {

    override fun run(vararg args: String?) {
        val allMeta = oracleMetaDao.getAllMeta()

        println(allMeta)
    }
}