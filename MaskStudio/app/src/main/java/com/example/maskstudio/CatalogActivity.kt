package com.example.maskstudio

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogActivity : AppCompatActivity() {

    private val fullMaskList = listOf(
        MaskItem("Oni Mask", "Traditional Japanese Demon Mask"),
        MaskItem("Kitsune Mask", "Japanese Sacred Fox Mask"),
        MaskItem("Gala Mask", "Elegantly Ornamented Venetian Half-Mask"),
        MaskItem("Classic Comedy Mask", "Traditional Theatre Smiling Mask"),
        MaskItem("Cyberpunk Mask", "Futuristic Sci-Fi Visor Mask")
    )

    private val displayedList = ArrayList<MaskItem>()
    private lateinit var adapter: MaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val etSearch = findViewById<EditText>(R.id.etSearchMask)
        val rvCatalog = findViewById<RecyclerView>(R.id.rvCatalog)

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        displayedList.addAll(fullMaskList)

        adapter = MaskAdapter(displayedList) { selectedMask ->
            val intent = Intent(this, CanvasActivity::class.java).apply {
                putExtra("MASK_NAME", selectedMask.name)
            }
            startActivity(intent)
        }

        rvCatalog.layoutManager = LinearLayoutManager(this)
        rvCatalog.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCatalog(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterCatalog(query: String) {
        displayedList.clear()
        if (query.isEmpty()) {
            displayedList.addAll(fullMaskList)
        } else {
            val lower = query.lowercase()
            for (item in fullMaskList) {
                if (item.name.lowercase().contains(lower) || item.description.lowercase().contains(lower)) {
                    displayedList.add(item)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}