package com.example.ftkshoppinglist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class FirebaseAuthViewModel : ViewModel() {
    var user = mutableStateOf<FirebaseUser?>(null)
    private var admin by mutableStateOf(false)

    init {
        if (user.value == null)
            user.value = Firebase.auth.currentUser
    }

    var emailInput by mutableStateOf("")
    var pwdInput by mutableStateOf("")
    var usernameInput by mutableStateOf("")

    var presetNameInput by mutableStateOf("")

    var logInState by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    fun registerUser() {
        logInState = "LOADING"
        Firebase.auth.createUserWithEmailAndPassword(emailInput, pwdInput)
            .addOnSuccessListener {
                user.value = it.user
                createUData()
                logInState = "SUCCESS"
                Log.d("FIREBASE", "Success")
            }.addOnFailureListener {
                logInState = "FAILURE"
                errorMessage = it.toString().substringAfter(":").trim()
                Log.e("FIREBASE", it.toString())
            }
    }

    fun signInUser() {
        logInState = "LOADING"
        Firebase.auth.signInWithEmailAndPassword(emailInput, pwdInput)
            .addOnSuccessListener {
                user.value = it.user
                logInState = "SUCCESS"
                Log.d("FIREBASE", "Log In Success")
            }
            .addOnFailureListener {
                logInState = "FAILURE"
                errorMessage = it.toString().substringAfter(":").trim()
                Log.e("FIREBASE", it.toString())
            }
    }

    private fun createUData() {
        var presetsArray = mutableListOf<ProductData>()
        user.value.let { fUser ->
            Firebase.firestore.collection("udata")
                .document(fUser!!.uid)
                .set(
                    mapOf(
                        "admin" to false,
                        "username" to usernameInput
                    )
                )
                .addOnSuccessListener {
                    Log.d("FIREBASE", "Add successful")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE", it.toString())
                }
            Firebase.firestore.collection("udata")
                .document(fUser.uid)
                .collection("presets")
                .document()
                .set(
                    presetsArray
                )
                .addOnSuccessListener {
                    Log.d("FIREBASE", "Add successful")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE", it.toString())
                }
        }
    }

    fun checkUser(): Boolean {
        return (user.value != null)
    }

    fun checkAdmin(): Boolean {
        user.value?.let { fUser ->
            Log.d("USER", fUser.uid)
            Firebase.firestore.collection("udata").document(fUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        admin = (document.data!!.get("admin") == true)
                        Log.d("FIREBASE", "Admin fetch successful")
                    } else {
                        Log.d("FIREBASE", "No such document")
                    }
                }.addOnFailureListener {
                    Log.e("FIREBASE", "Failed to fetch document by uid")
                }
        } ?: Log.d("USER", "No user info")

        return admin
    }

    fun savePreset(presetArray: List<ProductData>) {
        val productList = presetArray.map { it.toMap() }
        if (productList.isNotEmpty()) {
            user.value?.let { fUser ->
                Firebase.firestore.collection("udata")
                    .document(fUser.uid)
                    .collection("presets")
                    .add(mapOf(
                        "name" to presetNameInput,
                        "products" to productList))
                    .addOnSuccessListener {
                        Log.d("FIREBASE", "Add successful")
                    }
                    .addOnFailureListener {
                        Log.e("FIREBASE", it.toString())
                    }
            }
        }
    }

    fun logOut() {
        Firebase.auth.signOut()
        user.value = null
        admin = false
    }
}