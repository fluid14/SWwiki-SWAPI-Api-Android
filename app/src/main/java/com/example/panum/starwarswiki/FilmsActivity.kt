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
import kotlinx.android.synthetic.main.activity_films.*
import models.FilmQr
import rest.APIClient
import retrofit2.Call
import retrofit2.Response
import com.google.gson.Gson
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


class FilmsActivity : AppCompatActivity() {

    internal lateinit var searchFilmsInput: EditText
    internal lateinit var txtFilmsResult: TextView
    internal lateinit var btnFilmsSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("StarWarsWiki Films")
        setContentView(R.layout.activity_films)


         searchFilmsInput = findViewById<EditText>(R.id.searchFilmInput)
         btnFilmsSearch = findViewById<Button>(R.id.searchFilmBtn)
         txtFilmsResult = findViewById<TextView>(R.id.searchFilmsTextView)

         txtFilmsResult.setMovementMethod(ScrollingMovementMethod())
    }

    fun btnFilmsSearch_onClick(v: View) {
        searchFilms(searchFilmsInput.getText().toString())
        hideKeyboard()
    }


    private fun searchFilms(tagword: String) {
        val call = APIClient.get().searchFilms(tagword)

        call.enqueue(object : retrofit2.Callback<FilmQr> {

            override fun onFailure(call: Call<FilmQr>, t: Throwable) {
                searchFilmsTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<FilmQr>, response: Response<FilmQr>) {


                searchFilmInput.setText("")
                val films = response.body()
                Log.w("films", Gson().toJson(films))

                if (films != null) {
                    if (films.results.size != 0)
                    searchFilmsTextView.setText(Html.fromHtml(films.results[0].toString()))
                    else
                        searchFilmsTextView.setText("Not found!")
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
