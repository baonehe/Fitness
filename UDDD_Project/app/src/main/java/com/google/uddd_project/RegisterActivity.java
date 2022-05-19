package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.os.StrictMode;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextFirstName, editTextLastName, editTextPass, editTextPassConfirm;
    private FirebaseAuth auth;
    private boolean isNotExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.email_edit_text_register);
        editTextFirstName = findViewById(R.id.firstname_edit_text_register);
        editTextLastName = findViewById(R.id.lastname_edit_text_register);
        editTextPass = findViewById(R.id.password_edit_text_register);
        editTextPassConfirm = findViewById(R.id.confirmpass_edit_text_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString().trim();
                String firstname = editTextFirstName.getText().toString().trim();
                String lastname = editTextLastName.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
                String passconfirm = editTextPassConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                    editTextEmail.setError("Email is required");
                else if (!isEmailValid(email))
                    editTextEmail.setError("Invalid Email Address");
                else if (TextUtils.isEmpty(firstname))
                    editTextFirstName.setError("First name is required");
                else if (TextUtils.isEmpty(lastname))
                    editTextLastName.setError("Last name is required");
                else if (TextUtils.isEmpty(pass))
                    editTextPass.setError("Pass is required");
                else if (pass.length() < 6)
                    editTextPass.setError("Password must be >= 6 characters");
                else if (!pass.equals(passconfirm))
                    editTextPassConfirm.setError("Passwords isn't match!");
                else {
                    auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            isNotExist = task.getResult().getSignInMethods().isEmpty();
                            if (!isNotExist) {
                                Toast.makeText(RegisterActivity.this, "Email was used", Toast.LENGTH_SHORT).show();
                            } else {
                                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Verify", Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = auth.getCurrentUser();
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else
                                            Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });

                }
            }

            private boolean isEmailValid(String email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}