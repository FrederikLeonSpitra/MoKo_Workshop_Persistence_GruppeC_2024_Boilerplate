package com.example.room_showcase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao //DataAccessObject Hier stellen wir Funktionen für die Roomdatenbank auf.
interface WorkshopDao {

    @Upsert //Anders als Insert, diese Funktion wird wenn ein Eintrag mit dieser ID schon existiert diesen stattdessen Updaten.
    //Insert würde stattdessen meckern. Alternativ könnte eine (onConflict) lösung genutzt werden, dass sprengt aber heute den Rahmen.
    suspend fun upsertData(pommodoriTable: PommodoriTable) //Datenbank funktionen die eine Zusantsveränderung hervorrufen, benötoigen ein Suspend wegen Asnychronität.

    @Delete
    suspend fun deleteEntry(pommodoriTable: PommodoriTable)

    @Query("SELECT * FROM PommodoriTable ORDER BY Entry_1 ASC")
    fun  getDataOrderedbyEntry_1(): Flow<List<PommodoriTable>>

    @Query("SELECT * FROM PommodoriTable ORDER BY Entry_2 ASC")
    fun  getDataOrderedbyEntry_2(): Flow<List<PommodoriTable>>

    @Query("SELECT * FROM PommodoriTable ORDER BY Entry_3 DESC")
    fun  getDataOrderedbyEntry_3(): Flow<List<PommodoriTable>>

    @Query("SELECT * FROM PommodoriTable WHERE id=:id") //Sucht uns alle Ergebnisse heraus, die der ID entspricht, nach der wir suchen (:id ist der Query den wir übergeben, das id davor ist die Spalte in der DB das wir durchsuchen)
    fun getEntryById(id:Int): Flow<PommodoriTable>

    @Query("SELECT * FROM PommodoriTable WHERE Entry_2=:entry_2") //Sucht uns alle Ergebnisse heraus, die der ID entspricht, nach der wir suchen (:id ist der Query den wir übergeben, das id davor ist die Spalte in der DB das wir durchsuchen)
    fun getDataByString(entry_2:String): Flow<PommodoriTable>

}


/*CRUD
Create -> Upsert
Read-> Query
Update -> Upsert
Delete -> Delete

Entry_2=:entry_2
Collumnname =: Variablenname
 */