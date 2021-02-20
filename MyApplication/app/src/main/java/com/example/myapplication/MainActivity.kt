@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity() {
    private val REQUEST_TAKE_PHOTO = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
       if(prefs.getBoolean("first_time", true)){
            val myDB = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null)
            myDB.execSQL(
                    "CREATE TABLE IF NOT EXISTS class (code Varchar(200),name VARCHAR(200), class VARCHAR(10),  theme VARCHAR(100), lesson VARCHAR(200), weekday VARCHAR(200), Lessonnumber Int )"
            );
            var contentValue = ContentValues();
            var content1=ContentValues();
           contentValue.put("code", "инф-1");
           contentValue.put("name", "Меркулова Е.О.");
           contentValue.put("class", "11 А");
           contentValue.put("Theme", "6 задание ЕГЭ");
           contentValue.put("lesson", "Информатика");
           contentValue.put("weekday", 1 );
           contentValue.put("Lessonnumber", 3);
           myDB.insert("class", null, contentValue);
           content1.put("code", "хим");
           content1.put("name", "Терещенко О.Б.");
           content1.put("class", "10 А");
           content1.put("Theme", "Альдегиды-кетоны");
           content1.put("lesson", "Химия");
           content1.put("weekday", 1 );
           content1.put("Lessonnumber", 3);
           myDB.insert("class", null, content1);
           myDB.close()
            val editor = prefs.edit()
            editor.putBoolean("first_time", false)
            editor.commit()
        }
             /*else{
           val myDB = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null)
           myDB.execSQL(
                   "DROP TABLE class; "
           );
       }*/
        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            scanQRCode()
        }

    }

    private fun scanQRCode(){
        val integrate = IntentIntegrator(this).apply {
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        }
        integrate.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {

            }
            else {
                val randomIntent = Intent(this, MainActivity2::class.java)
                randomIntent.putExtra(MainActivity2.value,result.contents)
                startActivity(randomIntent)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
