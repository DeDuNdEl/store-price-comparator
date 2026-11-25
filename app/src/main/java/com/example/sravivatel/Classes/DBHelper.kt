package com.example.sravivatel.Classes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "localSQL", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun clearLocal_products_DB(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS local_products_DB ")
        db.execSQL("CREATE TABLE IF NOT EXISTS local_products_DB (Product_Name TEXT, Product_Cost_diksi INT, Product_Cost_paterocka INT, Product_Cost_magnit_celevaya INT, Product_Cost_perekrestok INT, Product_Weight TEXT, Product_Img_Url TEXT, Product_Category TEXT)")
        db.close()
    }

    fun insertToLocal_products_DB(Product_Name: String, Product_Cost_diksi: Long, Product_Cost_paterocka: Long, Product_Cost_magnit_celevaya: Long, Product_Cost_perekrestok: Long, Product_Weight: String, Product_Img_Url: String, Product_Category: Long){
        val values = ContentValues()
        val db = this.writableDatabase
        values.put("Product_Name", Product_Name)
        values.put("Product_Cost_diksi", Product_Cost_diksi)
        values.put("Product_Cost_paterocka", Product_Cost_paterocka)
        values.put("Product_Cost_magnit_celevaya", Product_Cost_magnit_celevaya)
        values.put("Product_Cost_perekrestok", Product_Cost_perekrestok)
        values.put("Product_Weight", Product_Weight)
        values.put("Product_Img_Url", Product_Img_Url)
        values.put("Product_Category", Product_Category)

        db.insert("local_products_DB",null ,values)

        db.close()
    }
    fun createPropertiesTable(){
        val db = this.writableDatabase
        db.execSQL("CREATE TABLE IF NOT EXISTS local_properties (date_of_update_DB TEXT, date_of_update_DB_text TEXT DEFAULT 'нет значения')")
        db.execSQL("CREATE TABLE IF NOT EXISTS local_products_DB (Product_Name TEXT, Product_Cost_lenta INT, Product_Cost_paterocka INT, Product_Cost_magnit_celevaya INT, Product_Cost_perekrestok INT, Product_Weight TEXT, Product_Img_Url TEXT, Product_Category TEXT)")

        val countQuery = "SELECT COUNT(*) FROM local_properties"
        val cursor = db.rawQuery(countQuery, null)

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        if (count == 0){
            val values = ContentValues()

            values.put("date_of_update_DB", "99")

            db.insert( "local_properties" ,null ,values)
        }

        db.close()
    }

    fun updatePropertiesTable(){
        val db = this.writableDatabase

        val date = SimpleDateFormat("dd").format(Date())

        val values = ContentValues()
        values.put("date_of_update_DB", date)

        db.update("local_properties", values, null, null)
    }

    fun updatePropertiesTableLastUpdateDB(date_text: String){
        val db = this.writableDatabase

        val date = SimpleDateFormat("dd").format(Date())

        val values = ContentValues()
        values.put("date_of_update_DB_text", date_text)
        db.update("local_properties", values, null, null)
    }

    fun getLastUpdateDB(): String{
        val db = this.writableDatabase

        val cursor = db.rawQuery("SELECT date_of_update_DB_text FROM local_properties", null)
        cursor.moveToNext()

        return cursor.getString(0)
    }

    fun getPropertiesTable(): String {
        val db = this.writableDatabase

        val cursor = db.rawQuery("SELECT date_of_update_DB FROM local_properties", null)
        cursor.moveToNext()

        return cursor.getString(0)
    }

    fun addCart(name: String) {

        val db = this.writableDatabase

        if(name in getTableNames()){
            Toast.makeText(context, "у корзин не могут быть одинаковые названия", Toast.LENGTH_SHORT).show()
        }
        else{
            db.execSQL("""CREATE TABLE "$name" (product_url TEXT, product_amount INT) """)
        }

        db.close()
    }


    fun getValuesFromCart(name: String?, url: String): Int {
        val db = this.writableDatabase

        val cursor = db.rawQuery("SELECT product_amount FROM $name WHERE product_url = '$url'", null)
        cursor.moveToNext()

        return cursor.getInt(0)
    }

    fun updateValuesFromCart(name: String?, newValue: Int, url: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("product_amount", newValue)
        db.update(name, values, "product_url = ?", arrayOf(url))
    }

    fun deleteValuesFromCart(name: String?, url: String) {
        val database = this.writableDatabase


        database.delete(name, "product_url = ?", arrayOf(url))
        database.close()
    }

    fun removeCart(name: String) {

        val db = this.writableDatabase

        db.execSQL("""DROP TABLE IF EXISTS "$name" """)
        db.close()
    }

    fun addValuesToCart(cart_name:String, product_url: String, product_amount: Int){
        val values = ContentValues()
        val db = this.writableDatabase
        values.put("product_url", product_url)
        values.put("product_amount", product_amount)

        db.insert("$cart_name",null ,values)
        db.close()
    }

    fun getTableData(tableName: String?): Array<Array<String>> {

        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName", null)

        val tableData = mutableListOf<Array<String>>()

        if (cursor.moveToFirst()) {
            do {
                val row = mutableListOf<String>()

                for (i in 0 until cursor.columnCount) {
                    row.add(cursor.getString(i))
                }

                tableData.add(row.toTypedArray())
            } while (cursor.moveToNext())
        }

        cursor.close()
        return tableData.toTypedArray()
    }

    @SuppressLint("Range")
    fun getTableNames(): ArrayList<String> {
        val tableNames = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val tableName = cursor.getString(cursor.getColumnIndex("name"))

                tableNames.add(tableName)
                cursor.moveToNext()
            }
        }

        cursor.close()
        tableNames.removeAt(tableNames.indexOf("android_metadata"))
        tableNames.removeAt(tableNames.indexOf("local_properties"))
        tableNames.removeAt(tableNames.indexOf("local_products_DB"))

        return tableNames
    }
}