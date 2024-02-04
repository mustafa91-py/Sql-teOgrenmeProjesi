package com.mustafauyar.sqliteogrenmeprojesi
import com.mustafauyar.sqliteogrenmeprojesi.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        try {
            val db = this.openOrCreateDatabase("Urunler", MODE_PRIVATE,null)
            // pythonda rastladığım te tırnak sorununu karşılaşmamak için üç trınak rasında kullandım execute u
            db.execSQL("""CREATE TABLE IF NOT EXISTS urunler(id INTIGER PRIMARY KEY,isim VARCHAR,fiyat INT)""")
            db.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ("Ayakkabı",100)""")

        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}