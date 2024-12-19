package com.example.shopinglistxml

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private lateinit var itemInput: EditText
    private lateinit var addButton: Button
    private val shoppingList = mutableListOf("Хлеб", "Молоко", "Яйца")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация View
        recyclerView = findViewById(R.id.recyclerView)
        itemInput = findViewById(R.id.itemInput)
        addButton = findViewById(R.id.addButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        shoppingListAdapter = ShoppingListAdapter(
            shoppingList,
            onDeleteClick = { item -> removeItem(item) },
            onEditClick = { item, position -> editItem(item, position) }
        )
        recyclerView.adapter = shoppingListAdapter

        // Логика добавления товара
        addButton.setOnClickListener {
            val newItem = itemInput.text.toString().trim()
            if (newItem.isNotEmpty()) {
                addItem(newItem)
                itemInput.text.clear() // Очистить поле ввода
            }
        }
    }

    private fun addItem(item: String) {
        shoppingList.add(item)
        shoppingListAdapter.notifyItemInserted(shoppingList.size - 1)
        recyclerView.scrollToPosition(shoppingList.size - 1) // Прокрутка к новому элементу
    }

    private fun removeItem(item: String) {
        val position = shoppingList.indexOf(item)
        if (position >= 0) {
            shoppingList.removeAt(position)
            shoppingListAdapter.notifyItemRemoved(position)
        }
    }

    private fun editItem(item: String, position: Int) {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(item)

        // Создаём диалог для редактирования
        AlertDialog.Builder(this)
            .setTitle("Редактировать товар")
            .setView(input)
            .setPositiveButton("Сохранить") { _, _ ->
                val newItem = input.text.toString().trim()
                if (newItem.isNotEmpty()) {
                    shoppingList[position] = newItem
                    shoppingListAdapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
