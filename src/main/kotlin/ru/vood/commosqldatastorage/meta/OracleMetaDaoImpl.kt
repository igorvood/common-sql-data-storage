package ru.vood.commosqldatastorage.meta

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OracleMetaDaoImpl(val jdbcOperations: JdbcOperations) : OracleMetaDao {
    override fun getAllMeta(): Map<String, TableMeta> {
        jdbcOperations.query("""select ATC.OWNER, ATC.TABLE_NAME, ATC.COMMENTS, ATC2.COLUMN_NAME, ATC2.DATA_TYPE, ATC2.DATA_LENGTH, ATC2.DATA_PRECISION, ATC2.DATA_SCALE, ATC2.NULLABLE
                                    from ALL_TAB_COMMENTS ATC
                                        join ALL_TAB_COLS ATC2 on (ATC.OWNER, ATC.TABLE_NAME) = ((ATC2.OWNER, ATC2.TABLE_NAME))
                                    where ATC.OWNER = 'JP'
                                    order by ATC.OWNER,
                                             ATC.TABLE_NAME,
                                             ATC2.COLUMN_ID""", ResultSetExtractor { rs ->
            val treeMap = TreeMap<String, TableMeta>()
            while (rs.next()) {
                val tabName = rs.getString(2)

                val (columns, uks) = treeMap.computeIfAbsent(tabName) { TableMeta(HashMap(), HashMap()) }
                columns[rs.getString(4)] = TableColumn(rs.getString(4))
            }
            treeMap
        }
        )

        TODO("Not yet implemented")
    }
}