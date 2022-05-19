package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.email_edit_text_login);
        editTextPass = findViewById(R.id.password_edit_text_login);
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
                if (TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPass.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please fill in the blanks", Toast.LENGTH_SHORT).show();
                } else {
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
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
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