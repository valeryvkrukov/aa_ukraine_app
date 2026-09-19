package org.aa.ukraine.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.aa.ukraine.core.database.Main
import org.aa.ukraine.core.database.MainDao
import javax.inject.Inject

interface MainRepository {
    val mainDatas: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultMainRepository @Inject constructor(
    private val mainDataDao: MainDao
) : MainRepository {

    override val mainDatas: Flow<List<String>> =
        mainDataDao.getMains().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        mainDataDao.insertMain(Main(name = name))
    }
}
