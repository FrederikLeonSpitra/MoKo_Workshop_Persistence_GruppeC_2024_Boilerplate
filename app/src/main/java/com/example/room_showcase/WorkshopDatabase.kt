package com.example.room_showcase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(
    entities = [PommodoriTable::class],
    version = 1 //this is needed for Migrations etc. Sprengt unseren Rahmen. Nicht relevant atm.
)
abstract class WorkshopDatabase : RoomDatabase() {

    abstract fun getDao(): WorkshopDao //Wir legen fest, dass eine Instanz eine getDao haben muss.

    companion object { //YOU CAN NOT EXPLAIN THIS SHIT! JUST TELL THEM TO TAKE IT! JUST MAKE SURE THEY UNDERSTAND WHY! And this is booilerplate
        @Volatile //warum? (das will kotlin so, just do it)
        private var INSTANCE: WorkshopDatabase? = null
        fun getDatabase(context: Context): WorkshopDatabase {
            val temp = INSTANCE
            if (temp != null) return temp //Zur Sicherheit, falls schon eine temp existiert, gib bitte diese uns zurück.
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkshopDatabase::class.java,
                    "Workshop_Persistence_C_DB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }       //Wir möchten als Return ein companion Object

    //val dao: WorkshopDao //Linked unsere Daos
}