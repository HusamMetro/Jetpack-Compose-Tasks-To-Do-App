package com.tuwaiq.husam.taskstodoapp.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tuwaiq.husam.taskstodoapp.data.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRepo {
    private val firebaseAuthInstance = FirebaseAuth.getInstance()
    private val fireStoreInstance = FirebaseFirestore.getInstance()

    var user: User = User()

    fun loadUserInformation() {
        val userUID = firebaseAuthInstance.currentUser?.uid
        val docRef = fireStoreInstance.collection("users").document("$userUID")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()!!
        }
    }

    fun logInFirebase(email: String, password: String): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result.postValue(task.isSuccessful)
            }
        return result
    }

    fun registerFirebase(email: String, password: String): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firebaseAuthInstance.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result.postValue(task.isSuccessful)
            }
        return result
    }

    fun forgotPasswordFirebase(email: String): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firebaseAuthInstance.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                result.postValue(task.isSuccessful)
            }
        return result
    }

    fun saveUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        val userCollectionRef = fireStoreInstance.collection("users")
        val userUid = firebaseAuthInstance.currentUser!!.uid
        try {
            userCollectionRef.document(userUid).set(user).await()
            withContext(Dispatchers.Main) {
                Log.e("FireStore", "Successfully saved data")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("FireStore", "${e.message}")
            }
        }
    }

    fun updateUserFirestore(name: String, phoneNumber: String): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val userUID = firebaseAuthInstance.currentUser?.uid
        val collectionRef = fireStoreInstance.collection("users")
        collectionRef.document(userUID.toString())
            .update(
                "username",
                name,
                "phoneNumber",
                phoneNumber
            ).addOnCompleteListener { task ->
                result.postValue(task.isSuccessful)
            }
        return result
    }

    fun signOutFirebase() {
        firebaseAuthInstance.signOut()
    }
}