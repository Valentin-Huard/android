package tp.lpdip.myapplication

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface Modele {

    @Insert
    fun insertTodo(todo: Todo): Long

    @Insert
    fun insertTodoList(todoList: List<Todo>)

    @Query("SELECT * FROM todo")
    fun fetchAllTodos(): List<Todo>

    @Query("SELECT * FROM todo WHERE category = :category")
    fun fetchTodoListByCategory(category: String): List<Todo>

    @Query("SELECT * FROM todo WHERE todo_id = :todoId")
    fun fetchTodoListById(todoId: Int): Todo

    @Update
    fun updateTodo(todo: Todo): Int

    @Delete
    fun deleteTodo(todo: Todo): Int
}