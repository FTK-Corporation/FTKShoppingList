package com.example.ftkshoppinglist

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: Uri,
    val aisle: String?,
    val category: String?,
    val subCategory: String?
)

class StorageViewModel : ViewModel() {
    var productExists by mutableStateOf(false)

    var idInput by mutableStateOf("")
    private val id: Int
        get() {
            return idInput.toIntOrNull() ?: 0
        }

    var name by mutableStateOf("")
    fun changeName(string: String) {
        name = string
    }

    var description by mutableStateOf("")
    fun changeDescription(string: String) {
        description = string
    }

    var imageUri by mutableStateOf<Uri?>(null)

    var aisle by mutableStateOf("")
    fun changeAisle(string: String) {
        aisle = string
    }

    var category by mutableStateOf("")
    fun changeCategory(string: String) {
        category = string
    }

    var subCategory by mutableStateOf("")
    fun changeSubCategory(string: String) {
        subCategory = string
    }

    fun addProduct() {
        Firebase.firestore.collection("productdata")
            .document(id.toString())
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    Log.d("FIREBASE", "Product exists!")
                    Log.d("FIREBASE DOCUMENT", doc.toString())
                    productExists = true
                } else {
                    Log.d("FIREBASE", "Product doesn't exist")
                    var imageRef = Firebase.storage.reference.child(id.toString())

                    imageUri?.let { u ->
                        imageRef.putFile(u)
                            .addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { remoteUri ->
                                    Firebase.firestore.collection("productdata")
                                        .document(id.toString())
                                        .set(
                                            Product(
                                                id,
                                                name,
                                                description,
                                                remoteUri,
                                                aisle,
                                                category,
                                                subCategory
                                            )
                                        )
                                }
                            }
                            .addOnFailureListener {
                                Log.e("FIREBASE STORAGE", it.message.toString())
                            }
                    } ?: Log.d("IMAGE", "Image URI is null")
                }


            }
            .addOnFailureListener {

            }


    }
}

