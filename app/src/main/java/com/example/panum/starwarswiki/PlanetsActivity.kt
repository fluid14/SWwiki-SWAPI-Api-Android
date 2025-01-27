package com.example.panum.starwarswiki

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import rest.APIClient
import retrofit2.Call
import retrofit2.Response
import com.google.gson.Gson
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_planets.*
import models.PlanetQr


class PlanetsActivity : AppCompatActivity() {

    internal lateinit var searchPlanetsInput: EditText
    internal lateinit var txtPlanetsResult: TextView
    internal lateinit var btnPlanetsSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("StarWarsWiki Planets")
        setContentView(R.layout.activity_planets)


        searchPlanetsInput = findViewById<EditText>(R.id.searchPlanetInput)
        btnPlanetsSearch = findViewById<Button>(R.id.searchPlanetBtn)
        txtPlanetsResult = findViewById<TextView>(R.id.searchPlanetTextView)

        txtPlanetsResult.setMovementMethod(ScrollingMovementMethod())
    }

    fun btnPlanetsSearch_onClick(v: View) {
        searchPlanets(searchPlanetsInput.getText().toString())
        hideKeyboard()
    }

    private fun searchPlanets(tagword: String) {
        val call = APIClient.get().searchPlanets(tagword)

        call.enqueue(object : retrofit2.Callback<PlanetQr> {

            override fun onFailure(call: Call<PlanetQr>, t: Throwable) {
                searchPlanetTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PlanetQr>, response: Response<PlanetQr>) {

                searchPlanetInput.setText("")
                val planets = response.body()
                Log.w("planets", Gson().toJson(planets))

                if (planets != null) {
                    if (planets.results.size != 0)
                        searchPlanetTextView.setText(Html.fromHtml(planets.results[0].toString()))
                    else
                        searchPlanetTextView.setText("Not Found!")
                }
            }
        })
    }

    fun AppCompatActivity.hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        // else {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        // }
    }
}
