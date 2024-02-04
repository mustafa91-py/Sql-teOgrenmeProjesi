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
            db.execSQL("""CREATE TABLE IF NOT EXISTS urunler(id INTIGER PRIMARY KEY,isim VARCHAR,fiyat INT)""")
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}