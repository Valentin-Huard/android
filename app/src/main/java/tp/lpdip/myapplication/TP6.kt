package tp.lpdip.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.app.Activity
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.support.design.widget.*
import android.view.View
import android.widget.*
import android.support.v7.widget.*
import java.util.ArrayList
import java.util.Arrays



class TP6 : AppCompatActivity(), RecyclerViewAdapter.ClickListener, AdapterView.OnItemSelectedListener {

    internal lateinit var myDatabase: MyDatabase
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var spinner: Spinner
    internal lateinit var recyclerViewAdapter: RecyclerViewAdapter
    internal lateinit var floatingActionButton: FloatingActionButton
    private val categories = arrayOf("All", "Android", "iOS", "Kotlin", "Swift")

    internal var todoArrayList = ArrayList<Todo>()
    internal var spinnerList = ArrayList(Arrays.asList(*categories))

    lateinit var updateTodo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tp6)

        initViews()
        myDatabase = Room.databaseBuilder(applicationContext, MyDatabase::class.java, MyDatabase.DB_NAME).fallbackToDestructiveMigration().build()
        checkIfAppLaunchedFirstTime()

        spinner.onItemSelectedListener = this
        spinner.setSelection(0)

        floatingActionButton.setOnClickListener { startActivityForResult(Intent(this@TP6, TodoNoteActivity::class.java), NEW_TODO_REQUEST_CODE) }

    }

    private fun initViews() {
        floatingActionButton = findViewById(R.id.fab)
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun launchIntent(id: Int) {
        startActivityForResult(Intent(this@TP6, TodoNoteActivity::class.java).putExtra("id", id), UPDATE_TODO_REQUEST_CODE)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {


        if (position == 0) {
            loadAllTodos()
        } else {
            val string = parent.getItemAtPosition(position).toString()
            loadFilteredTodos(string)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }


    @SuppressLint("StaticFieldLeak")
    private fun loadFilteredTodos(category: String) {
        object : AsyncTask<String, Void, List<Todo>>() {
            override fun doInBackground(vararg params: String): List<Todo> {
                return myDatabase.daoAccess().fetchTodoListByCategory(params[0])

            }

            override fun onPostExecute(todoList: List<Todo>) {
                recyclerViewAdapter.updateTodoList(todoList)
            }
        }.execute(category)

    }


    @SuppressLint("StaticFieldLeak")
    private fun fetchTodoById(todo_id: Int) {
        object : AsyncTask<Int, Void, Todo>() {
            protected override fun doInBackground(vararg params: Int?): Todo? {

                return params[0]?.let { myDatabase.daoAccess().fetchTodoListById(it) }

            }

            override fun onPostExecute(todo: Todo) {
                super.onPostExecute(todo)
                val inTitle = findViewById<EditText>(R.id.inTitle)
                val inDescription = findViewById<EditText>(R.id.inDescription)
                inTitle.setText(todo.name)
                inDescription.setText(todo.description)
                spinner.setSelection(spinnerList.indexOf(todo.category))

                updateTodo = todo
            }
        }.execute(todo_id)

    }

    @SuppressLint("StaticFieldLeak")
    private fun fetchTodoByIdAndInsert(id: Int) {
        object : AsyncTask<Int, Void, Todo>() {
            override fun doInBackground(vararg params: Int?): Todo? {
                return params[0]?.let { myDatabase.daoAccess().fetchTodoListById(it) }

            }

            override fun onPostExecute(todoList: Todo) {
                recyclerViewAdapter.addRow(todoList)
            }
        }.execute(id)

    }
    @SuppressLint("StaticFieldLeak")
    private fun loadAllTodos() {
        object : AsyncTask<String, Void, List<Todo>>() {
            override fun doInBackground(vararg params: String): List<Todo> {
                return myDatabase.daoAccess().fetchAllTodos()
            }

            override fun onPostExecute(todoList: List<Todo>) {
                recyclerViewAdapter.updateTodoList(todoList)
            }
        }.execute()
    }

    private fun buildDummyTodos() {
        var todo = Todo()
        todo.name = "Android Retrofit Tutorial"
        todo.description = "Cover a tutorial on the Retrofit networking library using a RecyclerView to show the data."
        todo.category = "Android"

        todoArrayList.add(todo)

        todo = Todo()
        todo.name = "iOS TableView Tutorial"
        todo.description = "Covers the basics of TableViews in iOS using delegates."
        todo.category = "iOS"

        todoArrayList.add(todo)

        todo = Todo()
        todo.name = "Kotlin Arrays"
        todo.description = "Cover the concepts of Arrays in Kotlin and how they differ from the Java ones."
        todo.category = "Kotlin"

        todoArrayList.add(todo)

        todo = Todo()
        todo.name = "Swift Arrays"
        todo.description = "Cover the concepts of Arrays in Swift and how they differ from the Java and Kotlin ones."
        todo.category = "Swift"

        todoArrayList.add(todo)
        insertList(todoArrayList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            //reset spinners
            spinner.setSelection(0)

            if (requestCode == NEW_TODO_REQUEST_CODE) {
                val id = data!!.getLongExtra("id", -1)
                Toast.makeText(applicationContext, "Row inserted", Toast.LENGTH_SHORT).show()
                fetchTodoByIdAndInsert(id.toInt())

            } else if (requestCode == UPDATE_TODO_REQUEST_CODE) {

                val isDeleted = data!!.getBooleanExtra("isDeleted", false)
                val number = data.getIntExtra("number", -1)
                if (isDeleted) {
                    Toast.makeText(applicationContext, number.toString() + " rows deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, number.toString() + " rows updated", Toast.LENGTH_SHORT).show()
                }

                loadAllTodos()

            }


        } else {
            Toast.makeText(applicationContext, "No action done by user", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun insertList(todoList: List<Todo>) {
        object : AsyncTask<List<Todo>, Void, Void>() {
            override fun doInBackground(vararg params: List<Todo>): Void? {
                myDatabase.daoAccess().insertTodoList(params[0])

                return null

            }

            override fun onPostExecute(voids: Void) {
                super.onPostExecute(voids)
            }
        }.execute(todoList)

    }

    private fun checkIfAppLaunchedFirstTime() {
        val PREFS_NAME = "SharedPrefs"

        val settings = getSharedPreferences(PREFS_NAME, 0)

        if (settings.getBoolean("firstTime", true)) {
            settings.edit().putBoolean("firstTime", false).apply()
            buildDummyTodos()
        }
    }

    companion object {

        val NEW_TODO_REQUEST_CODE = 200
        val UPDATE_TODO_REQUEST_CODE = 300
    }
}