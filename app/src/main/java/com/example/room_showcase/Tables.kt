package com.example.room_showcase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PommodoriTable( //Unsere Tabllendefinition
    @PrimaryKey(autoGenerate = true) //standartmäßig ist der auf false, also muss das noch geändert werden.
    val id: Int = 0,

    val Entry_1: String,

    val Entry_2: String,

    var Entry_3: Int
){
    override fun toString(): String {
        return "$id: $Entry_1, $Entry_2, $Entry_3"
    }
}


//autogenerator spart uns viel Zeit, ist aber Standartmäßig auf false gestellt,
// bitte dran denken diesen auf true umzuschreiben

//Um sicher zu gehen, dass immer eine ID vorhanden ist, einen Standartwert setzen.















