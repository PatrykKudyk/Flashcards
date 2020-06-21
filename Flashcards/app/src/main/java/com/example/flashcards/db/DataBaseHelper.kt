package com.example.flashcards.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

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

}