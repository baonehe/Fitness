package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPass;
    private FirebaseAuth auth;
    private TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        editTextEmail = findViewById(R.id.email_edit_text_login);
        editTextPass = findViewById(R.id.password_edit_text_login);
        textInputLayoutEmail= findViewById(R.id.email_text_input_login);
        textInputLayoutPassword= findViewById(R.id.password_text_input_login);
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonForgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editTextEmail.getText().toString())){
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutEmail.setError("Please enter your Email");


                }else if (TextUtils.isEmpty(editTextPass.getText().toString())) {
                    textInputLayoutEmail.setErrorEnabled(false);
                    textInputLayoutEmail.setError(null);
                    textInputLayoutPassword.setErrorEnabled(true);
                    textInputLayoutPassword.setError("Please enter your Password");
                } else {
                    textInputLayoutEmail.setError(null);
                    textInputLayoutPassword.setError(null);
                    textInputLayoutEmail.setErrorEnabled(false);
                    textInputLayoutPassword.setErrorEnabled(false);
                    auth = FirebaseAuth.getInstance();
                    String emailText = editTextEmail.getText().toString();
                    String passText = editTextPass.getText().toString();
                    loginUser(emailText, passText);
                }

            }
        });
    }

    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, LoadingPageActivity.class));
                            finish();
                        }
                    },1000);
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(MainActivity.this, "Account is incorrect or doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        });

//        FirebaseUser fUser = auth.getCurrentUser();
//        if(fUser.isEmailVerified()){
//            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            finish();
//        }
//        else Toast.makeText(MainActivity.this, "Email isn't verify", Toast.LENGTH_SHORT).show();
    }
}