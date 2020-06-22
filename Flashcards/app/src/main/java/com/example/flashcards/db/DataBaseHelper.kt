package com.example.flashcards.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import android.provider.BaseColumns
import com.example.flashcards.models.Flashcard
import com.example.flashcards.models.MyPackage

object TableInfo : BaseColumns {
    const val DATABASE_NAME = "Flashcards"
    const val TABLE_NAME_PACKAGES = "Packages"
    const val TABLE_COLUMN_PACKAGES_NAME = "name"
    const val TABLE_NAME_FLASHCARDS = "Flashcards"
    const val TABLE_COLUMN_FLASHCARDS_PACKAGE = "package"
    const val TABLE_COLUMN_FLASHCARDS_QUESTION = "question"
    const val TABLE_COLUMN_FLASHCARDS_ANSWER = "answer"

}

object BasicCommand {
    const val SQL_CREATE_TABLE_PACKAGES =
        "CREATE TABLE ${TableInfo.TABLE_NAME_PACKAGES} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_PACKAGES_NAME} TEXT NOT NULL)"

    const val SQL_CREATE_TABLE_FLASHCARDS =
        "CREATE TABLE ${TableInfo.TABLE_NAME_FLASHCARDS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_QUESTION} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_ANSWER} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE_PACKAGES = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PACKAGES}"
    const val SQL_DELETE_TABLE_FLASHCARDS = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PACKAGES}"
}

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_FLASHCARDS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_FLASHCARDS)

        onCreate(db)
    }

    fun getPackagesList(): ArrayList<MyPackage> {
        var packagesList = ArrayList<MyPackage>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_PACKAGES}"
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var myPackage = MyPackage(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_PACKAGES_NAME))
                )
                packagesList.add(myPackage)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return packagesList
    }

    fun addPackage(name: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_NAME, name)
        val success = db.insert(TableInfo.TABLE_NAME_PACKAGES, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun updatePackage(myPackage: MyPackage): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_NAME, myPackage.title)
        val success = db.update(
            TableInfo.TABLE_NAME_PACKAGES, values, BaseColumns._ID + "=?",
            arrayOf(myPackage.id.toString())
        ).toLong()
        return Integer.parseInt("$success") != -1
    }


    fun deletePackage(id: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(TableInfo.TABLE_NAME_PACKAGES, BaseColumns._ID + "=?", arrayOf(id.toString()))
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun getFlashcardsList(packageId: Long): ArrayList<Flashcard> {
        var flascardsList = ArrayList<Flashcard>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_FLASHCARDS} where ${TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE} = " +
                packageId.toString()
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var flashcard = Flashcard(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE)).toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_QUESTION)),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_ANSWER))
                )
                flascardsList.add(flashcard)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return flascardsList
    }

    fun addFlashcard(packageId: Long, question: String, answer: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE, packageId)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_QUESTION, question)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_ANSWER, answer)
        val success = db.insert(TableInfo.TABLE_NAME_FLASHCARDS, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun updateFlashcard(flashcard: Flashcard): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_QUESTION, flashcard.question)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_ANSWER, flashcard.answer)
        val success = db.update(
            TableInfo.TABLE_NAME_FLASHCARDS, values, BaseColumns._ID + "=?",
            arrayOf(flashcard.id.toString())
        ).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }


    fun deleteFlashcard(id: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(
                TableInfo.TABLE_NAME_FLASHCARDS,
                BaseColumns._ID + "=?",
                arrayOf(id.toString())
            )
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}