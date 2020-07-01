package tp.lpdip.myapplication

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

import java.io.Serializable

@Entity(tableName = "todo")
class Todo : Serializable {

    @PrimaryKey(autoGenerate = true)
    var todo_id: Int = 0

    var name: String? = null

    var description: String? = null

    var category: String? = null

    @Ignore
    var priority: String? = null

}