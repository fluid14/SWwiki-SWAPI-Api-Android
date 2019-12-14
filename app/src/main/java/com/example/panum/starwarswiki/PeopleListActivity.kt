package com.example.panum.starwarswiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_people.*
import models.PeopleQr
import rest.APIClient
import retrofit2.Call
import retrofit2.Response
import kotlin.random.Random

class PeopleListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        searchPeopleList("")
    }


    private fun searchPeopleList(tagword: String) {
        val call = APIClient.get().searchRandomPeople("")

        call.enqueue(object : retrofit2.Callback<PeopleQr> {

            override fun onFailure(call: Call<PeopleQr>, t: Throwable) {
                searchPeopleTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PeopleQr>, response: Response<PeopleQr>) {
                val peoples = response.body()
                if (peoples != null) {
                    if (peoples.results.size != 0) {
                        for (i in 0..9) {
                            var peopleName = peoples.results[i].name.toString()
                            val ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                            val button_dynamic = Button(baseContext)
                            button_dynamic.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            button_dynamic.text = peopleName
                            button_dynamic.setOnClickListener {
                                val intent = Intent(baseContext, PeopleActivity::class.java)
                                intent.putExtra("PeopleName", peopleName)
                                startActivity(intent)
                            }
                            ll_main.addView(button_dynamic)
                        }}
                }
            }
        })
    }

}
