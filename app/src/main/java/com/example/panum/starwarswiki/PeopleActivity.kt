package com.example.panum.starwarswiki

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_people.*
import models.PeopleQr
import rest.APIClient
import retrofit2.Call
import retrofit2.Response
import kotlin.random.Random
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.AbsSavedState
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.NotificationCompat


class PeopleActivity : AppCompatActivity() {


    internal lateinit var searchPeopleInput: EditText
    internal lateinit var txtPeopleResult: TextView
    internal lateinit var btnPeopleSearch: Button
    internal lateinit var btnPeopleList: Button

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val chanelId = "com.example.panum.starwarswiki"
    var title = ""
    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("StarWarsWiki People")
        setContentView(R.layout.activity_people)

        val extras = getIntent().getExtras()
        if (null != extras) {
            val value = extras.getString("PeopleName")
            searchPeople(value.toString())
            hideKeyboard();
        }

        searchPeopleInput = findViewById<EditText>(R.id.searchPeopleInput)
        btnPeopleSearch = findViewById<Button>(R.id.searchPeopleBtn)
        btnPeopleList = findViewById<Button>(R.id.listPeopleBtn)
        txtPeopleResult = findViewById<TextView>(R.id.searchPeopleTextView)

        txtPeopleResult.setMovementMethod(ScrollingMovementMethod())

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        testNtBtn.setOnClickListener {
            sendNotification()
        }

//        Wysyłanie powiadomienia co 4h
        val handler = Handler()
        Handler().postDelayed(object : Runnable {
            override fun run() {
                sendNotification()
                handler.postDelayed(this, 14400000)
            }
        }, 14400000)
    }


    fun btnPeopleSearch_onClick(v: View) {
        searchPeople(searchPeopleInput.getText().toString())
        hideKeyboard()
    }

    fun btnPeopleList_onClick(v: View) {
        val peopleList = Intent(this, PeopleListActivity::class.java)
        startActivity(peopleList)
    }

    private fun searchPeople(tagword: String) {
        val call = APIClient.get().searchPeople(tagword)

        call.enqueue(object : retrofit2.Callback<PeopleQr> {

            override fun onFailure(call: Call<PeopleQr>, t: Throwable) {
                searchPeopleTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PeopleQr>, response: Response<PeopleQr>) {


                searchPeopleInput.setText("")
                val peoples = response.body()

                if (peoples != null) {
                    if (peoples.results.size != 0)
                        searchPeopleTextView.setText(Html.fromHtml(peoples.results[0].toString()))
                    else
                        searchPeopleTextView.setText("Not Found!")
                }
            }
        })
    }

//    Pobieranie informacji z Api do powiadomień
    private fun searchPeopleNotification(tagword: String) {
        val call = APIClient.get().searchRandomPeople(tagword)

        call.enqueue(object : retrofit2.Callback<PeopleQr> {

            override fun onFailure(call: Call<PeopleQr>, t: Throwable) {
                searchPeopleTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PeopleQr>, response: Response<PeopleQr>) {
                val peoples = response.body()
                if (peoples != null) {
                    if (peoples.results.size != 0) {
                        val randomValuesPeople =  Random.nextInt(0, 10)
                        val randomValuesProperty = Random.nextInt(1, 6)
                        title = peoples.results[randomValuesPeople].name.toString()

                        when(randomValuesProperty) {
                            1 -> description = "is ".plus(peoples.results[randomValuesPeople].height.toString()).plus("cm tall")
                            2 -> description = "weighs ".plus(peoples.results[randomValuesPeople].mass.toString()).plus("kg")
                            3 -> description = "has ".plus(peoples.results[randomValuesPeople].hairColor.toString()).plus(" hair color")
                            4 -> description = "has ".plus(peoples.results[randomValuesPeople].skinColor.toString()).plus(" skin color")
                            5 -> description = "has ".plus(peoples.results[randomValuesPeople].eyeColor.toString()).plus(" eye color")
                            6 -> description = "was born in ".plus(peoples.results[randomValuesPeople].birthYear.toString()).plus(" cm tall")
                            else -> description = ""
                        } }
                    else {
                        title = "Not Found!"
                    }
                }
            }
        })
    }


// Wysyłanie powiadomienia
    private fun sendNotification(){
        searchPeopleNotification("")
        val intent = Intent(this, PeopleActivity::class.java)
        intent.putExtra("PeopleName", title)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName,R.layout.notification_layout)
        contentView.setTextViewText(R.id.tv_content, "Text Notification111")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                chanelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,chanelId)
                .setContentTitle("Do you know that " + title)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContentTitle("Do you know that " + title)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
        }

        builder.setAutoCancel(true);
        notificationManager.notify(1234,builder.build())
    }


//    Ukrywanie klawiatury
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

