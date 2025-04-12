package dev.alexpace.kassist.ui.components.victim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexpace.kassist.domain.models.FormData

@Composable
fun HelpRequestForm(
    modifier: Modifier = Modifier,
    onSubmitSuccess: (String) -> Unit = {}, // Callback for success
    onSubmitError: (String) -> Unit = {}   // Callback for error
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = it.isBlank()
                },
                label = { Text("Name") },
                isError = nameError,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            if (nameError) {
                Text(
                    text = "Name is required",
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = it.isNotEmpty() && !it.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex())
                },
                label = { Text("Email") },
                isError = emailError,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (emailError) {
                Text(
                    text = "Invalid email format",
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }

            // Phone Field
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = it.isNotEmpty() && !it.matches("\\d{10}".toRegex())
                },
                label = { Text("Phone") },
                isError = phoneError,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            if (phoneError) {
                Text(
                    text = "Enter a valid 10-digit phone number",
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }

            // Submit Button
            Button(
                onClick = {
                    nameError = name.isBlank()
                    emailError = email.isNotEmpty() && !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex())
                    phoneError = phone.isNotEmpty() && !phone.matches("\\d{10}".toRegex())

                    if (!nameError && !emailError && !phoneError) {
                        submitFormData(
                            name = name,
                            email = email,
                            phone = phone,
                            onSuccess = { onSubmitSuccess("Form submitted successfully!") },
                            onError = { onSubmitError("Failed to submit form.") }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text("Submit", fontSize = 16.sp)
            }
        }
    }
}

// Function to collect form data and submit it
fun submitFormData(
    name: String,
    email: String,
    phone: String,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    // Create FormData object
    val frmData = FormData(
        name = name,
        email = email,
        phone = phone
    )

    // Simulate API call
    try {
        // TODO: Submission here
        val success = true
        if (success) {
            onSuccess()
        } else {
            onError()
        }
    } catch (e: Exception) {
        onError()
    }
}