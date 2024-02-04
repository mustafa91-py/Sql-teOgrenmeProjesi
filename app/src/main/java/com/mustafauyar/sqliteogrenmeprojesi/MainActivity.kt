package com.mustafauyar.sqliteogrenmeprojesi
import android.database.sqlite.SQLiteDatabase
import com.mustafauyar.sqliteogrenmeprojesi.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDb:SQLiteDatabase
    private lateinit var myShop:HashMap<String,Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        try {
            veritabaniOlustur()
            // tablo oluştur
            veriTabaniUrunEkle()
            // urunleri ekle
            binding.textViewDb.text = veritabaniGoster()
//            veritabaniGoster()
            // veritabani ürünleri göster
            binding.textViewIndirim.text = indirimYap(oran = 50)
            // urunlere indirim yap
            binding.textViewSonFiyat.text = veritabaniGoster()
            // tekrar veri tabani göster

        } catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun veritabaniOlustur(){
        myDb = this.openOrCreateDatabase("Urunler", MODE_PRIVATE,null)
        // pythonda rastladığım tek tırnak sorununu karşılaşmamak için üç tırınak arasında kullandım execute u
        myDb.execSQL("DROP TABLE IF EXISTS urunler")// veri tabanı table sil
        // her seferinde baştan oluşturmak için urunler tablosunu sil

        myDb.execSQL("""CREATE TABLE IF NOT EXISTS urunler(id INTEGER PRIMARY KEY,isim VARCHAR,fiyat INT)""")
    }
    fun veriTabaniUrunEkle(){
        myShop = HashMap()
        myShop.put("Ayakkabi",100)
        myShop["Takke"] = 10
        myShop["gozluk"] = 750
        myShop["saat"] = 87
        myShop["elbise"] = 150

        myShop.forEach { (k, v) ->
            Log.d("Cursor eklenen ürünler", "$k : $v")
            myDb.execSQL("""INSERT INTO urunler (isim, fiyat) VALUES ('$k',$v)""") // isimler belirtirken tek tırnak kullanılmalıdır
            // her bir key ve value leri tabloya kaydet Cursor dict etiketi ile logcat de göster
        }

    }
    fun indirimYap(oran:Int=10):String{
        var indirimText = String()
        myShop.forEach {(k,v) ->
            val indirim = (v*oran/100)
            val yeniFiyat = v-indirim
            indirimText += "$k : $v  indirim oranı = % $oran  indirimli fiyat :${v-indirim}\n"
            Log.d("cursor indirim","$k : $v  indirim oranı = % $oran  indirimli fiyat :${v-indirim}")
            veritabaniUrunGuncelle(k,yeniFiyat)
        }
    return indirimText
    }
    fun veritabaniGoster(db:SQLiteDatabase=myDb):String{
        val cursor = db.rawQuery("SELECT * FROM urunler",null) // verileri al hepsini
        val idColumIndex = cursor.getColumnIndex("id")
        val nameColumnIndex = cursor.getColumnIndex("isim")
        val priceColumnIndex = cursor.getColumnIndex("fiyat")
        val blockTag = "Cursor Database"
        var toShow = String()
        while (cursor.moveToNext()){
            var text = String()
            text += "ID : ${cursor.getInt(idColumIndex)} ,"
            text += "İSİM : ${cursor.getString(nameColumnIndex)} ,"
            text += "FİYAT : ${cursor.getInt(priceColumnIndex)}"
            toShow += text +"\n"
//            Log.d(blockTag, text)
        }
        Log.d(blockTag,toShow)
        cursor.close()
        return toShow
    }
    fun veritabaniUrunGuncelle(urun:String,yeniFiyat:Int){
           myDb.execSQL("""UPDATE urunler SET fiyat = $yeniFiyat WHERE isim ='$urun'""") // elbise nin fiyatını güncelle 999 olarak

    }
    fun veritabaniUrunSil(){
//            db.execSQL("""DELETE  FROM  urunler  WHERE id = 1""") // id 1 olanı sil tablodan

    }
}