package com.mustafauyar.sqliteogrenmeprojesi
import com.mustafauyar.sqliteogrenmeprojesi.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        try {
            val db = this.openOrCreateDatabase("Urunler", MODE_PRIVATE,null)
            // pythonda rastladığım tek tırnak sorununu karşılaşmamak için üç tırınak arasında kullandım execute u
            db.execSQL("DROP TABLE IF EXISTS urunler")// veri tabanı table sil
            // her seferinde baştan oluşturmak için urunler tablosunu sil

            db.execSQL("""CREATE TABLE IF NOT EXISTS urunler(id INTEGER PRIMARY KEY,isim VARCHAR,fiyat INT)""")
            // tablo oluştur
            val myShop = HashMap<String,Int>() // ürünler sözlüğü
            myShop.put("Ayakkabi",100)
            myShop["Takke"] = 10
            myShop["gozluk"] = 750
            myShop["saat"] = 87
            myShop.forEach { (k, v) ->
                Log.d("Cursor dict", "$k : $v")
                db.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ('$k',$v)""") // isimler belirtirken tek tırnak kullanılmalıdır
                // her bir key ve value leri tabloya kaydet Cursor dict etiketi ile logcat de göster
            }
//            db.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ("Ayakkabı",100)""")
//            db.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ("Takke",10)""")
//            db.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ("Gozluk",750)""")


            val cursor = db.rawQuery("SELECT * FROM urunler",null)
            val idColumIndex = cursor.getColumnIndex("id")
            val nameColumnIndex = cursor.getColumnIndex("isim")
            val priceColumnIndex = cursor.getColumnIndex("fiyat")
            val blockTag = "Cursor ürünler"
            while (cursor.moveToNext()){
                Log.d(blockTag, "ID : ${cursor.getInt(idColumIndex)}")
                Log.d(blockTag,"İSİM : ${cursor.getString(nameColumnIndex)}")
                Log.d(blockTag,"FİYAT : ${cursor.getInt(priceColumnIndex)}")
            }
            cursor.close()
//            db.execSQL("DROP TABLE urunler")// veri tabanı table sil
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}