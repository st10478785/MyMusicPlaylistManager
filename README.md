App Overview 

This is an application made using Android Studios, coded using Kotlin about, a music playlist manager app. On the app you should 
enter the information regarding the song title, artist, a rating and a comment.


Features
- User Friendly Interface: Simple, intuitive design with straightforward navigation
- Text Boxes: To type your answer into.
- Feedback: Immediate visual feedback displaying the information you wrote if touch the show playlist button.
- Result Review: Detailed breakdown of all enter information
- Main Menu: Takes you to the main menu without closing the application
- Exit Function: Close the application when finished

App Structure 
1. MainActivity (Start Screen)
- Welcome message and app introduction ( txtHeading ) 
- AddPlaylist ()
- ViewPlaylist ()
- Exit Applictaion

2. Detailed View Screen (Quiz Screen)
- Heading ( txtHeading)
- AddPlaylist ()
- ViewPlaylist ()
- Exit Applictaion
- Main Menu


Links


GitHub:  https://github.com/st10478785/MyMusicPlaylistManager




References: 

Harvard reference link style :

By Anon Year: 2025 Container: Sharepoint.com URL: https://advtechonline.sharepoint.com/:w:/r/sites/TertiaryStudents/IIE Student Materials/New Student Materials CAT/IMAD5112/2025/Term 1/IMAD5112_MM.docx?d=wa1ff62f08e1a47bc99bdca07ae24427d&csf=1&web=1&e=3UqSH7

The independent Institution of Education, 2025. Introduction to mobile application development: Module manual 2025 (first edition). The independent Institution of education.



First Screen ( Activity Main )
Second Screen ( Detailed View Screen )


Code:

First Page :
package vcmsa.ci.mymusicplaylistmanager

// Vishay Gosai
// ST10478785

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Arrays to store Song details
    private val songTitle = mutableListOf<String>()
    private val artistsName = mutableListOf<String>()
    private val ratingBar = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // UI Elements
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnView = findViewById<Button>(R.id.btnView)
        val txtHeading = findViewById<TextView>(R.id.txtHeading)
        val txtName2 = findViewById<TextView>(R.id.txtName2)

        val edtSongTitle = findViewById<EditText>(R.id.edtSongTitle)
        val edtArtistsName = findViewById<EditText>(R.id.edtArtistsName)
        val edtRatingBar = findViewById<EditText>(R.id.edtRatingBar)
        val edtComments = findViewById<EditText>(R.id.edtComments)
        val btnAddPlaylist1 = findViewById<Button>(R.id.btnAddPlaylist1)
        val btnNext = findViewById<Button>(R.id.btnNext)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)


        // Button Add
        btnAdd.setOnClickListener {
            // make Home sceen invisible
            btnAdd.visibility = View.INVISIBLE
            btnView.visibility = View.INVISIBLE
            txtHeading.visibility = View.INVISIBLE
            txtName2.visibility = View.INVISIBLE

            // Make LinearLayout visible
            linearLayout.visibility = View.VISIBLE
        }

        // Button Exit terminates app
        btnExit.setOnClickListener {
            finishAffinity()
        }


        // Add Playlist Button Click Listener
        btnAddPlaylist1.setOnClickListener {
            val songTitle = edtSongTitle.text.toString()
            val artistsName = edtArtistsName.text.toString()
            val ratingBar = edtRatingBar.text.toString()
            val comment = edtComments.text.toString()

            try {
                // Validate inputs
                if (songTitle.isBlank() || artistsName.isBlank() || ratingBar.isBlank()) {
                    throw IllegalArgumentException("All fields except comments must be filled!")
                }

                val ratingBar = ratingBar.toIntOrNull()
                    ?: throw NumberFormatException("Quantity must be a number!")

                // Add item details to arrays
                addItem(songTitle, artistsName, ratingBar, comment)

                // Clear input fields
                edtSongTitle.text.clear()
                edtArtistsName.text.clear()
                edtRatingBar.text.clear()
                edtComments.text.clear()

                // Error Handling
                Toast.makeText(this, "Song Titles added successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this,
                    "Invalid rating! Please enter a valid rating.",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "An unexpected error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Next Button Click Listener
        btnNext.setOnClickListener {
            if (songTitle.isEmpty()) {
                Toast.makeText(
                    this,
                    "No Song Titles to display. Please add a song title first!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Pass data to DetailedViewScreenActivity
                val intent = Intent(this, DetailedViewScreenActivity::class.java)
                intent.putStringArrayListExtra("Song Title", ArrayList(songTitle))
                intent.putStringArrayListExtra("Artist's Name", ArrayList(artistsName))
                intent.putIntegerArrayListExtra("Rating Bar", ArrayList(ratingBar))
                intent.putStringArrayListExtra("Comments", ArrayList(comments))
                startActivity(intent)
            }
        }
        // Button View to next page
        btnView.setOnClickListener {
            val intent = Intent(this, DetailedViewScreenActivity::class.java)
            startActivity(intent)
        }
    }


    // Function to add item details to parallel arrays
    private fun addItem(songTitle: String, artistsName: String, ratingBar: Int, comment: String) {
        this.songTitle.add(songTitle)
        this.artistsName.add(artistsName)
        this.ratingBar.add(ratingBar)
        comments.add(comment)
    }
}




DetailedViewScreen:
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
