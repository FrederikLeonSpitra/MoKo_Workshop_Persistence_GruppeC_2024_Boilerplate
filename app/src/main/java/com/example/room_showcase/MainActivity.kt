package com.example.room_showcase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.room_showcase.repository.WorkshopRepository
import com.example.room_showcase.ui.theme.Room_ShowcaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Room_ShowcaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        val context = this

        lifecycleScope.launch { //weil wir wollen bei launch das haben + ohne können wir keinen suspend functions nutzen
            val debugTag = "roomdb"
            val repository = WorkshopRepository(context)
            val tableData = PommodoriTable(
                id = 1,
                Entry_1 = "Lunges",
                Entry_2 = "Liegestütze",
                Entry_3 = 10
            )
            Log.d(debugTag, "Database Created")
            repository.insertData(tableData)
            Log.d(debugTag, "1 dataset inserted")

            val search = repository.getDataByID(tableData.id)
            Log.d("$debugTag - Inserted", search.toString())

            repository.updateData(tableData.copy(Entry_3 = tableData.Entry_3 + 10))

            val updated = repository.getDataByID(tableData.id)
            Log.d("$debugTag - Updated", updated.toString())

            /*val results = repository.getOrderedData()
            for((i, data) in results.withIndex()){
                Log.d("debugTag - $i",data.Entry_3.toString())
            }*/ //This does not work, and creates a timeout. Frag mich nicht warum.
            repository.deleteData(tableData)
            Log.d(debugTag, "Deleted entry ${tableData.id}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Room_ShowcaseTheme {
        Greeting("Android")
    }
}


//Ich würde halt das grundsätzliche Setup zeigen, vor allem wie die innere Logik der Programmarchitektur,
// wie das Repository mit dem Dao und der Database