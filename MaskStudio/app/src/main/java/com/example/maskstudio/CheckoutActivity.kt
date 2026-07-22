package com.example.maskstudio

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.net.URLEncoder

class CheckoutActivity : AppCompatActivity() {

    private lateinit var mapWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnBack = findViewById<Button>(R.id.btnCheckoutBack)
        val etAddress = findViewById<EditText>(R.id.etAddressSearch)
        val btnSearchAddress = findViewById<Button>(R.id.btnAutoFillAddress)
        mapWebView = findViewById(R.id.mapWebView)

        val rgPayment = findViewById<RadioGroup>(R.id.rgPayment)
        val cardDetailsLayout = findViewById<LinearLayout>(R.id.cardDetailsLayout)
        val gcashDetailsLayout = findViewById<LinearLayout>(R.id.gcashDetailsLayout)
        val etCardExpiry = findViewById<EditText>(R.id.etCardExpiry)
        val etGcashNumber = findViewById<EditText>(R.id.etGcashNumber)
        val btnPlaceOrder = findViewById<Button>(R.id.btnPlaceOrder)

        btnBack.setOnClickListener { finish() }

        mapWebView.settings.javaScriptEnabled = true
        mapWebView.webViewClient = WebViewClient()
        loadEmbeddedMap("Quezon City, Metro Manila")

        btnSearchAddress.setOnClickListener {
            val query = etAddress.text.toString().trim()
            if (query.isNotEmpty()) {
                loadEmbeddedMap(query)
            } else {
                Toast.makeText(this, "Please enter an address to search", Toast.LENGTH_SHORT).show()
            }
        }

        rgPayment.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbCard -> {
                    cardDetailsLayout.visibility = View.VISIBLE
                    gcashDetailsLayout.visibility = View.GONE
                }
                R.id.rbGcash -> {
                    cardDetailsLayout.visibility = View.GONE
                    gcashDetailsLayout.visibility = View.VISIBLE
                }
                else -> {
                    cardDetailsLayout.visibility = View.GONE
                    gcashDetailsLayout.visibility = View.GONE
                }
            }
        }

        etCardExpiry.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting || s == null) return
                isFormatting = true

                val clean = s.toString().replace("/", "")
                if (clean.length >= 2) {
                    val formatted = "${clean.substring(0, 2)}/${clean.substring(2)}"
                    etCardExpiry.setText(formatted)
                    etCardExpiry.setSelection(formatted.length)
                }

                isFormatting = false
            }
        })

        btnPlaceOrder.setOnClickListener {
            val address = etAddress.text.toString().trim()
            if (address.isEmpty()) {
                Toast.makeText(this, "Please provide a delivery address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedPaymentId = rgPayment.checkedRadioButtonId
            if (selectedPaymentId == R.id.rbGcash && etGcashNumber.text.toString().trim().length < 11) {
                Toast.makeText(this, "Please enter a valid 11-digit GCash number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val paymentMethod = when (selectedPaymentId) {
                R.id.rbCard -> "Credit/Debit Card"
                R.id.rbGcash -> "GCash"
                else -> "Cash on Delivery"
            }

            Toast.makeText(this, "Order Confirmed via $paymentMethod!\nDelivering to: $address", Toast.LENGTH_LONG).show()

            val intent = Intent(this, CatalogActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    private fun loadEmbeddedMap(location: String) {
        val encodedLocation = URLEncoder.encode(location, "UTF-8")
        val embedUrl = "https://maps.google.com/maps?q=$encodedLocation&t=&z=15&ie=UTF8&iwloc=&output=embed"

        val htmlData = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
            <style>
                body, html { margin: 0; padding: 0; width: 100%; height: 100%; background-color: #000000; overflow: hidden; }
                iframe { width: 100%; height: 100%; border: 0; }
            </style>
        </head>
        <body>
            <iframe 
                src="$embedUrl" 
                allowfullscreen="" 
                loading="lazy">
            </iframe>
        </body>
        </html>
    """.trimIndent()

        mapWebView.loadDataWithBaseURL("https://maps.google.com", htmlData, "text/html", "UTF-8", null)
    }
}