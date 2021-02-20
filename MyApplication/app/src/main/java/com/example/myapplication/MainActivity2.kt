package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDate
import java.util.*

class MainActivity2 : AppCompatActivity() {
    companion object{
        const val value = "total_count"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val textView: TextView = findViewById(R.id.name)
        val textView1: TextView = findViewById(R.id.clas)
        val textView2: TextView = findViewById(R.id.lesson)
        val textView3: TextView = findViewById(R.id.theme)
        val textView4: TextView=findViewById(R.id.time)
        val data = intent.getStringExtra(value)
        val myDB = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null)
        val myreader  = myDB.rawQuery("SELECT weekday,lessonnumber,code,name,class, lesson, theme from class", null)

       val c = Calendar.getInstance()
        var weekday = c.get(Calendar.DAY_OF_WEEK);
        if(weekday==1){
            weekday=7
        }
        else{
            weekday-=1;
        }
        var lessonnum=0;
        when(c.get(Calendar.HOUR_OF_DAY)){
            9->lessonnum=1
            10->lessonnum=2
            11->lessonnum=3
            12->lessonnum=4
            13->lessonnum=5
            14->lessonnum=6
            15->lessonnum=7
            16->lessonnum=8
            17->lessonnum=9
        }
        while (myreader.moveToNext()) {
            if(weekday==myreader.getInt(myreader.getColumnIndex("weekday"))){
                if(myreader.getString(myreader.getColumnIndex("code"))==data){
                    if(lessonnum==myreader.getInt(myreader.getColumnIndex("Lessonnumber"))){
                        val name= "ФИО учителя: "+myreader.getString(myreader.getColumnIndex("name"))
                        val clas= "Класс: "+ myreader.getString(myreader.getColumnIndex("class"))
                        val lesson= "Предмет: "+ myreader.getString(myreader.getColumnIndex("lesson"))
                        val theme= "Тема урока: "+ myreader.getString(myreader.getColumnIndex("theme"))
                        val time = "Время"+c.get(Calendar.HOUR_OF_DAY).toString() + ":" +c.get(Calendar.MINUTE).toString()
                        textView.text = name;
                        textView1.text = clas;
                        textView2.text = lesson;
                        textView3.text = theme;
                        textView4.text = time;

                    }
                }
            }
            }
        }
    }
/*val code:String = myreader.getString(myreader.getColumnIndex("code"))
            if(code==data){
                val name: String = myreader.getString(myreader.getColumnIndex("name"))
            }
                else{
                myDB.close()
                val randomIntent = Intent(this, MainActivity::class.java)
                val code: String = myreader.getString(myreader.getColumnIndex("code"))
                startActivity(randomIntent)*/