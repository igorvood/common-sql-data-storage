package ru.vood.commosqldatastorage.meta

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashSet

@Repository
class OracleMetaDaoImpl(val jdbcOperations: JdbcOperations) : OracleMetaDao {
    override fun getAllMeta(): TreeMap<String, TableMeta> {

        return jdbcOperations.query("""select ATC.OWNER, ATC.TABLE_NAME, ATC.COMMENTS, ATC2.COLUMN_NAME, ATC2.DATA_TYPE, ATC2.DATA_LENGTH, ATC2.DATA_PRECISION, ATC2.DATA_SCALE, ATC2.NULLABLE
                                    from ALL_TAB_COMMENTS ATC
                                        join ALL_TAB_COLS ATC2 on (ATC.OWNER, ATC.TABLE_NAME) = ((ATC2.OWNER, ATC2.TABLE_NAME))
                                    where ATC.OWNER = 'JP'
                                    order by ATC.OWNER,
                                             ATC.TABLE_NAME,
                                             ATC2.COLUMN_ID""", ResultSetExtractor { rs ->
            val tabs = TreeMap<String, TableMeta>()
            while (rs.next()) {
                val tabName = rs.getString(2).toLowerCase()

                val (columns, uks) = tabs.computeIfAbsent(tabName) { TableMeta(HashMap(), HashMap()) }
                val colName = rs.getString(4).toLowerCase()
                columns[colName] = TableColumn(colName)
            }

            tabs.entries.forEach {tab->
                val uks = getUk(tab.key.toUpperCase())
                uks.forEach { uk ->
                    val key = uk.key.toLowerCase()
                    val u = tab.value.uks.computeIfAbsent(key) { Unique(key, HashSet()) }
                     u.column.add(tab.value.columns[])
                }

            }


            tabs
        }
        )!!
    }

    private fun getUk(tabName: String): HashMap<String, HashSet<String>> {
        jdbcOperations.query("""select  AC.OWNER, AC.TABLE_NAME, ac.CONSTRAINT_NAME, ACC.COLUMN_NAME
                                        from ALL_CONSTRAINTS AC
                                            join ALL_CONS_COLUMNS ACC on (ACC.OWNER, ACC.TABLE_NAME, ACC.CONSTRAINT_NAME) = ((AC.OWNER, AC.TABLE_NAME, ac.CONSTRAINT_NAME))
                                        where AC.OWNER = 'JP' and AC.TABLE_NAME = :1
                                            and AC.CONSTRAINT_TYPE in ('P', 'U')""",
                ResultSetExtractor { rs ->
                    val uks = HashMap<String, HashSet<String>>()
                    while (rs.next()) {
                        val uk = rs.getString(3).toLowerCase()
                        val cols: HashSet<String> = uks.computeIfAbsent(uk) { HashSet() }
                        cols.add(rs.getString(4).toLowerCase())
                    }
                    uks
                },
                tabName
        )
    }
}