package com.example.panum.starwarswiki

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val chanelId = "com.example.panum.starwarswiki"
    private val description = "Test notification"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val peopleBtn = findViewById<Button>(R.id.peopleBtn)
        val planetsBtn = findViewById<Button>(R.id.planetsBtn)
        val filmsBtn = findViewById<Button>(R.id.filmsBtn)

    }

    fun peopleBtn_onClick(v: View) {
        val people = Intent(this@MainActivity, PeopleActivity::class.java)
        startActivity(people)
    }

    fun plantesBtn_onClick(v: View) {
        val planets = Intent(this@MainActivity, PlanetsActivity::class.java)
        startActivity(planets)
    }

    fun filmsBtn_onClick(v: View) {
        val films = Intent(this@MainActivity, FilmsActivity::class.java)
        startActivity(films)
    }
}
