package tp.lpdip.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*

import java.util.ArrayList
import java.util.Arrays
import tp.lpdip.myapplication.R.id.spinner
import tp.lpdip.myapplication.R.id.inTitle



class TodoNoteActivity : AppCompatActivity() {

        internal lateinit var spinner: Spinner
        internal lateinit var inTitle: EditText
        internal lateinit var inDesc: EditText
        internal lateinit var btnDone: Button
        internal lateinit var btnDelete: Button
        internal var isNewTodo = false

        private val categories = arrayOf("Android", "iOS", "Kotlin", "Swift")

        var spinnerList = ArrayList(Arrays.asList(*categories))
        internal lateinit var myDatabase: MyDatabase

        internal lateinit var updateTodo: Todo

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_todo_note)
            val toolbar = findViewById<Toolbar>(R.id.toolbar)

            spinner = findViewById<Spinner>(R.id.spinner)
            inTitle = findViewById(R.id.inTitle)
            inDesc = findViewById<EditText>(R.id.inDescription)
            btnDone = findViewById<Button>(R.id.btnDone)
            btnDelete = findViewById<Button>(R.id.btnDelete)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            myDatabase = Room.databaseBuilder(applicationContext, MyDatabase::class.java, MyDatabase.DB_NAME).build()

            val todo_id = intent.getIntExtra("id", -100)

            if (todo_id == -100)
                isNewTodo = true

            if (!isNewTodo) {
                fetchTodoById(todo_id)
                btnDelete.visibility = View.VISIBLE
            }

            btnDone.setOnClickListener {
                if (isNewTodo) {
                    val todo = Todo()
                    todo.name = inTitle.text.toString()
                    todo.description = inDesc.text.toString()
                    todo.category = spinner.selectedItem.toString()

                    insertRow(todo)
                } else {

                    updateTodo.name = inTitle.text.toString()
                    updateTodo.description = inDesc.text.toString()
                    updateTodo.category = spinner.selectedItem.toString()

                    updateRow(updateTodo)
                }
            }

            btnDelete.setOnClickListener { deleteRow(updateTodo) }
        }

    @SuppressLint("StaticFieldLeak")
    private fun fetchTodoById(todo_id: Int) {
        object : AsyncTask<Int, Void, Todo>() {
            override fun doInBackground(vararg params: Int?): Todo? {

                return params[0]?.let { myDatabase.daoAccess().fetchTodoListById(it) }

            }

            override fun onPostExecute(todo: Todo) {
                super.onPostExecute(todo)
                inTitle.setText(todo.name)
                inDesc.setText(todo.description)
                spinner.setSelection(spinnerList.indexOf(todo.category))

                updateTodo = todo
            }
        }.execute(todo_id)

    }

        @SuppressLint("StaticFieldLeak")
        private fun insertRow(todo: Todo) {
            object : AsyncTask<Todo, Void, Long>() {
                override fun doInBackground(vararg params: Todo): Long? {
                    return myDatabase.daoAccess().insertTodo(params[0])
                }

                override fun onPostExecute(id: Long?) {
                    super.onPostExecute(id)

                    val intent = intent
                    intent.putExtra("isNew", true).putExtra("id", id)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }.execute(todo)

        }

        @SuppressLint("StaticFieldLeak")
        private fun deleteRow(todo: Todo) {
            object : AsyncTask<Todo, Void, Int>() {
                override fun doInBackground(vararg params: Todo): Int? {
                    return myDatabase.daoAccess().deleteTodo(params[0])
                }

                override fun onPostExecute(number: Int?) {
                    super.onPostExecute(number)

                    val intent = intent
                    intent.putExtra("isDeleted", true).putExtra("number", number)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }.execute(todo)

        }


        @SuppressLint("StaticFieldLeak")
        private fun updateRow(todo: Todo) {
            object : AsyncTask<Todo, Void, Int>() {
                override fun doInBackground(vararg params: Todo): Int? {
                    return myDatabase.daoAccess().updateTodo(params[0])
                }

                override fun onPostExecute(number: Int?) {
                    super.onPostExecute(number)

                    val intent = intent
                    intent.putExtra("isNew", false).putExtra("number", number)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }.execute(todo)

        }

    }
