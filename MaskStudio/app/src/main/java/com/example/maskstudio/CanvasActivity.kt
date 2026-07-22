package com.example.maskstudio

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        val selectedMaskName = intent.getStringExtra("MASK_NAME") ?: "Oni Mask"

        val canvasView = findViewById<MaskCanvasView>(R.id.maskCanvas)
        canvasView.setMaskType(selectedMaskName)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnProceedCheckout = findViewById<Button>(R.id.btnProceedCheckout)

        btnBack.setOnClickListener { finish() }

        findViewById<View>(R.id.colorRed).setOnClickListener { canvasView.setColor(Color.RED) }
        findViewById<View>(R.id.colorBlue).setOnClickListener { canvasView.setColor(Color.BLUE) }
        findViewById<View>(R.id.colorYellow).setOnClickListener { canvasView.setColor(Color.YELLOW) }
        findViewById<View>(R.id.colorGreen).setOnClickListener { canvasView.setColor(Color.GREEN) }

        btnProceedCheckout.setOnClickListener {
            val intent = Intent(this@CanvasActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }
}