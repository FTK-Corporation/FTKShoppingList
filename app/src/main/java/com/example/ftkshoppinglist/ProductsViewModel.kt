package com.example.ftkshoppinglist

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductsViewModel: ViewModel() {
    var products by mutableStateOf(mutableListOf<ProductData>())

    var list by mutableStateOf(mutableListOf<ProductData>())

    var popupControl by mutableStateOf<ProductData?>(null)

    var posProvider = WindowCenterOffsetPositionProvider()

    fun fetchProducts() {
        Firebase.firestore.collection("productdata")
            .get()
            .addOnSuccessListener {
                val p = mutableListOf<ProductData>()
                it.documents.forEach { doc ->
                    Log.e("--", "--")
                    p.add(
                        ProductData(
                            doc.get("id").toString(),
                            doc.get("description").toString(),
                            doc.get("imageUri").toString(),
                            doc.get("name").toString()
                        )
                    )
                }
                products = p;
            }
    }
}