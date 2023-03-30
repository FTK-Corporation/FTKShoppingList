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

class FirebaseAuthViewModel : ViewModel() {
    private var user = mutableStateOf<FirebaseUser?>(null)
    private var admin by mutableStateOf(false)

    fun registerUser(email: String, pwd: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.value = it.user
                createUData()
                Log.d("FIREBASE", "Success")
            }.addOnFailureListener {
                Log.e("FIREBASE", it.toString())
            }
    }

    fun signInUser(email: String, pwd: String) {
        Firebase.auth.signInWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.value = it.user
                Log.d("FIREBASE", "Log In Success")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", it.toString())
            }
    }

    private fun createUData() {
        user.value.let { fUser ->
            Firebase.firestore.collection("udata")
                .document(fUser!!.uid)
                .set(
                    mapOf(
                        "admin" to false
                    )
                )
                .addOnSuccessListener {
                    Log.d("FIREBASE", "Add successful")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE", it.toString())
                }
        }
    }

    fun checkUser():Boolean {
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
}