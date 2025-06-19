package vcmsa.ci.mymusicplaylistmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class DetailedViewScreenActivity : AppCompatActivity() {

    // Arrays to store Song details
    private var songTitle = mutableListOf<String>()
    private var artistsName = mutableListOf<String>()
    private var ratingBar = mutableListOf<Int>()
    private var comments = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view_screen)

        // UI Elements
        val txtAnswer = findViewById<TextView>(R.id.txtAnswer)
        val listView = findViewById<ListView>(R.id.ListView)
        val txtAnswer2 = findViewById<TextView>(R.id.txtAnswer2)
        val btnShowAll = findViewById<Button>(R.id.btnShowAll)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnBackToMain = findViewById<Button>(R.id.btnBackToMain)

        // Retrive data from MainActivity
        songTitle = intent.getStringArrayListExtra("Song Title") as MutableList<String>
        artistsName = intent.getStringArrayListExtra("Artist's Name") as MutableList<String>
        ratingBar = intent.getIntegerArrayListExtra("Rating Bar") as MutableList<Int>
        comments = intent.getStringArrayListExtra("Comments") as MutableList<String>

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songTitle)
        listView.adapter = adapter

        // btnShowAll to display the data in the ListView
        // diplay the  grouped data : songTitle , artistsName , ratingBar , comments
        btnShowAll.setOnClickListener {

           displayPlaylist(listView, songTitle.toString(), artistsName, ratingBar, comments)

        }

        // btnCalculate to calculate the average rating
        // display the average rating in the txtAnswer
        btnCalculate.setOnClickListener {
            var sum = 0
            for (i in ratingBar) {
                sum += i
            }
            val average = sum / ratingBar.size
            txtAnswer2.text = "The average rating is $average"

        }

        // btnBackToMain to go back to MainActivity
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }

    private fun displayPlaylist(
        listView: ListView,
        filter: String,
        artistsName: MutableList<String>,
        ratingBar: MutableList<Int>,
        comments: MutableList<String>
    ) {
        val displayList = mutableListOf<String>() // List to hold the items to display

        try {
            // Loop through the arrays and add items to the display list based on the filter
            for (i in songTitle.indices) {
                val itemDetails = "Name: ${songTitle[i]}\n" +
                        "Category: ${this.artistsName[i]}\n" +
                        "Quantity: ${this.ratingBar[i]}\n" +
                        "Comments: ${this.comments[i]}"
                displayList.add(itemDetails)
            }
            // Create an ArrayAdapter to display the items in the ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
            listView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
