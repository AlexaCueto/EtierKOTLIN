package com.example.etierkotlin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment



class LoginDialog : DialogFragment() {
    private val username = "admin"
    private val password = "1234"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater // Use requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_login, null)

        val usernameInput = view.findViewById<EditText>(R.id.editUsername)
        val passwordInput = view.findViewById<EditText>(R.id.editPassword)
        val loginButton = view.findViewById<Button>(R.id.btnLogin)

        val dialog = builder.setView(view).create()

        loginButton.setOnClickListener {
            val enteredUsername = usernameInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            if (enteredUsername == username && enteredPassword == password) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                // Redirect to MainActivity
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                dismiss() // Call dismiss() on the DialogFragment itself

            } else {
                Toast.makeText(requireContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show() // Use requireContext()
            }
        }

        return dialog
    }
}
