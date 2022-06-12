package com.google.uddd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePass extends AppCompatActivity {
    EditText edtoldpass,edtnewpass,edtconfirmpass;
    Button updatepass;
    String email,oldpass,newpass,confirm;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth=FirebaseAuth.getInstance();
        edtconfirmpass= findViewById(R.id.confirmpass_edit_text_register);
        edtnewpass=findViewById(R.id.password_edit_text_register);
        edtoldpass=findViewById(R.id.oldpassword_edit_text_register);
        updatepass=findViewById(R.id.buttonUpdate);
        email = auth.getCurrentUser().getEmail();
        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass=edtoldpass.getText().toString().trim();
                newpass=edtnewpass.getText().toString().trim();
                confirm=edtconfirmpass.getText().toString().trim();
                if(!oldpass.isEmpty()) {
                    if(!newpass.isEmpty()) {
                        if (!newpass.equals(confirm))
                            edtconfirmpass.setError("Confirm password is not correct");
                        else {
                            onclickchangepassword();
                        }
                    }else edtnewpass.setError("Please fill your new password");
                }else edtoldpass.setError("Please fill your old password");

            }
        });

    }
    public void onclickchangepassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = newpass;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePass.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePass.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            reAuth();
                        }
                    }
                });
    };
    public void reAuth(){
        auth.signInWithEmailAndPassword(email,oldpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                    edtoldpass.setError("Password is not correct");
                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                onclickchangepassword();
                            }
                        }
                    });

                }
            }
        });
    };
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