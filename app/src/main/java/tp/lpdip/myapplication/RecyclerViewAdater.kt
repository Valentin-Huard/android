package tp.lpdip.myapplication

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

class RecyclerViewAdapter(clickListener: ClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val todoList: MutableList<Todo>
    private val clickListener: RecyclerViewAdapter.ClickListener

    init {
        this.clickListener = clickListener
        todoList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val todo = todoList[position]
        holder.txtName.text = todo.name
        holder.txtNo.text = "#" + todo.todo_id.toString()
        holder.txtDesc.text = todo.description
        holder.txtCategory.text = todo.category

    }

    override fun getItemCount(): Int {
        return todoList.size
    }


    fun updateTodoList(data: List<Todo>) {
        todoList.clear()
        todoList.addAll(data)
        notifyDataSetChanged()
    }

    fun addRow(data: Todo) {
        todoList.add(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtName: TextView
        var txtNo: TextView
        var txtDesc: TextView
        var txtCategory: TextView
        var cardView: CardView

        init {

            txtNo = view.findViewById(R.id.txtNo)
            txtName = view.findViewById(R.id.txtName)
            txtDesc = view.findViewById(R.id.txtDesc)
            txtCategory = view.findViewById(R.id.txtCategory)
            cardView = view.findViewById(R.id.cardView)
            cardView.setOnClickListener { clickListener.launchIntent(todoList[adapterPosition].todo_id) }
        }
    }

    interface ClickListener {
        fun launchIntent(id: Int)
    }
}