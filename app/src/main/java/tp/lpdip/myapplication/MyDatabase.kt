package tp.lpdip.myapplication

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Todo::class), version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun daoAccess(): Modele

    companion object {

        val DB_NAME = "app_db"
    }

}