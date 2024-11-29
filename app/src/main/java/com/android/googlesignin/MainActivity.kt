package com.android.googlesignin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                account?.let {
                    googleAuthForFirebase(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Sign out the user if previously logged in
        firebaseAuth.signOut()

        // Set click listener for Google Sign-In button
        findViewById<AppCompatButton>(R.id.btn_GoogleSignIn).setOnClickListener {
            // Configure Google Sign-In
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))  // Use your client ID from Firebase Console
                .requestEmail()
                .build()

            val signInClient = GoogleSignIn.getClient(this, signInOptions)
            val signInIntent = signInClient.signInIntent

            // Launch Google Sign-In activity
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Authenticate with Firebase using Google Account credentials
    private fun googleAuthForFirebase(googleAccount: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(googleAccount.idToken, null)

        // Perform Firebase authentication asynchronously
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                firebaseAuth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    // Show success message on the main thread
                    Toast.makeText(
                        this@MainActivity,
                        "You are Successfully Logged In",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    // Show error message on the main thread
                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
