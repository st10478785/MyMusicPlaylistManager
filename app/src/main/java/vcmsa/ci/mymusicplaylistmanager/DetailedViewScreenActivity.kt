package vcmsa.ci.mymusicplaylistmanager

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailedViewScreenActivity : AppCompatActivity() {

    // Arrays to store Song details
    private var songTitle = mutableListOf<String>()
    private var artistsName = mutableListOf<String>()
    private var ratingBar = mutableListOf<Int>()
    private var comments = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view_screen)

        // UI Elements
        val listView = findViewById<ListView>(R.id.ListView)
        val btnShowAll = findViewById<Button>(R.id.btnShowAll)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnBackToMain = findViewById<Button>(R.id.btnBackToMain)

        // Retrieve data passed from MainActivity (if available)
        songTitle = intent.getStringArrayListExtra("Song Title")?.toMutableList() ?: mutableListOf()
        artistsName = intent.getStringArrayListExtra("Artist's Name")?.toMutableList() ?: mutableListOf()
        ratingBar = intent.getIntegerArrayListExtra("Rating Bar")?.toMutableList() ?: mutableListOf()
        comments = intent.getStringArrayListExtra("comments")?.toMutableList() ?: mutableListOf()

        // Show all items when "Show All Items" is clicked
        btnShowAll.setOnClickListener {
            displayItems(listView, "all")
        }

        // Show Songs Ratings when "Calculate" is clicked
        btnCalculate.setOnClickListener {
            displayItems(listView, "Rating")
        }

        // Handle "Back to Main" button click (close the activity)
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to display the items in the ListView
    private fun displayItems(listView: ListView, filter: String) {
        val displayList = mutableListOf<String>()

        try {
            // Loop through the arrays and add items to the display list based on the filter
            for (i in songTitle.indices) {
                val itemDetails = "Songe Title: ${songTitle[i]}\n" +
                        "Artist's Name: ${artistsName[i]}\n" +
                        "Rating Bar: ${ratingBar[i]}\n" +
                        "Comments: ${comments[i]}"

                if (filter == "all" || (filter == "Ratings" && ratingBar[i] >= 2)) {
                    displayList.add(itemDetails)
                }
            }

            // Check if the displayList is empty
            if (displayList.isEmpty()) {
                Toast.makeText(this, "No playlist to display!", Toast.LENGTH_SHORT).show()
            } else {
                // Display the items in the ListView using an ArrayAdapter
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
                listView.adapter = adapter
            }
        } catch (e: Exception) {
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
