package com.example.panum.starwarswiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun peopleBtn_onClick(v: View) {
        val people = Intent(this@MainActivity, PeopleActivity::class.java)
        startActivity(people)
    }

    fun poepleListBtn_onClick(v: View) {
        val peopleList = Intent(this@MainActivity, PeopleListActivity::class.java)
        startActivity(peopleList)
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
