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
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_people.*
import models.PeopleQr
import rest.APIClient
import retrofit2.Call
import retrofit2.Response

class PeopleActivity : AppCompatActivity() {

    internal lateinit var searchPeopleInput: EditText
    internal lateinit var txtPeopleResult: TextView
    internal lateinit var btnPeopleSearch: Button

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

        searchPeopleInput = findViewById<EditText>(R.id.searchPeopleInput)
        btnPeopleSearch = findViewById<Button>(R.id.searchPeopleBtn)
        txtPeopleResult = findViewById<TextView>(R.id.searchPeopleTextView)

        txtPeopleResult.setMovementMethod(ScrollingMovementMethod())

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        testNtBtn.setOnClickListener {

            searchPeopleNotification("6")

            val intent = Intent(this, LauncherActivity::class.java)
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
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
            }else{
                builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
            }

            notificationManager.notify(1234,builder.build())
        }
    }

    fun btnPeopleSearch_onClick(v: View) {
        searchPeople(searchPeopleInput.getText().toString())
    }

    private fun searchPeople(tagword: String) {
        val call = APIClient.get().searchPeople(tagword)

        call.enqueue(object : retrofit2.Callback<PeopleQr> {

            override fun onFailure(call: Call<PeopleQr>, t: Throwable) {
                searchPeopleTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PeopleQr>, response: Response<PeopleQr>) {


                Log.d("APIPlug", "Successfully response fetched")
                searchPeopleInput.setText("")
                val peoples = response.body()
                Log.w("Peoples", Gson().toJson(peoples))

                if (peoples != null) {
                    if (peoples.results.size != 0)
                        searchPeopleTextView.setText(peoples.results[0].toString())
                    else
                        searchPeopleTextView.setText("Your request was not found!")
                }


            }
        })
    }

    private fun searchPeopleNotification(tagword: String) {
        val call = APIClient.get().searchRandomPeople(tagword)

        call.enqueue(object : retrofit2.Callback<PeopleQr> {

            override fun onFailure(call: Call<PeopleQr>, t: Throwable) {
                searchPeopleTextView.setText("Error: $t")
            }

            override fun onResponse(call: Call<PeopleQr>, response: Response<PeopleQr>) {


                Log.d("APIPlug", "Successfully response fetched")
                val peoples = response.body()
                Log.w("Peoples", Gson().toJson(peoples))

                if (peoples != null) {
                    if (peoples.results.size != 0) {
                        title = peoples.results[0].name.toString()
                        description = peoples.results[0].birthYear.toString()
                    }
                    else {
                        title = "Your request was not found!"
                    }
                }


            }
        })
    }
}
