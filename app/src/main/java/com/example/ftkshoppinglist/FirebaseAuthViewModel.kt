package com.example.ftkshoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthViewModel: ViewModel() {
    private var user = mutableStateOf<FirebaseUser?>(null)

    fun registerUser(email: String, pwd: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.value = it.user
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
}