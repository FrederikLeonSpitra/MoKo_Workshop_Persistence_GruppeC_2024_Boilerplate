package com.example.room_showcase.repository

import android.content.Context
import com.example.room_showcase.PommodoriTable
import com.example.room_showcase.WorkshopDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

class WorkshopRepository(context: Context) {
    val dao = WorkshopDatabase.getDatabase(context).getDao() //hier erstellen wir nun die Instanz

    suspend fun insertData(data: PommodoriTable) {
        dao.upsertData(data)
    }

    suspend fun updateData(data: PommodoriTable) { //muss wieder suspend sein. In der klammer kommt was:wo wir
        dao.upsertData(data)
    }

    fun getDataByID(id: Int): PommodoriTable =
        runBlocking { //Hier muss nach Typ Int gesucht werden, nicht nach Pommodoritable, weil wir nur die ID spalte durchsuchen, und die ist immer int.
            dao.getEntryById(id).first() //in diesem Fall, ist der Typ ein anderer,
        }

    fun getDataByString(data: String): PommodoriTable = runBlocking {
        dao.getDataByString(data)
            .first() //in diesem Fall ist der Typ dann String, weil wir in der Tabelle nach den Typen String suchen.
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getOrderedData() =
        runBlocking { //no return needed as lastline is automaticly returned. Runblocking sorgt dafür, dass unsere Asnchrone funktion in der Main synchron ausgeführt wird. Das funktioniert, weil die Application tatsächlich alles andere stoppt, bis die operation ausgeführt wurde. Da ich och keine Ahungn von Flows habe, ist das dass beste.
            dao.getDataOrderedbyEntry_3().flatMapConcat { it.asFlow() }
                .toList()//I HAVE NO IDEA! Quelle:https://stackoverflow.com/questions/59550674/how-to-convert-coroutines-flowlistt-to-listt

        }

    suspend fun deleteData(data: PommodoriTable) {
        dao.deleteEntry(data)
    }


    /*Why do we make2?
    For educational purposes.
    Wir können hier Funktionen definieren, die es im Dao so nicht gibt, aber wir können nur auf die
    funktionalitäten die dort definiert wurden zugreifen. Ergo: Diese beide Funktionen machen genau das selbe.
    */


}

