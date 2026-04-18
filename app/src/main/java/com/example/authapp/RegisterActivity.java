package com.example.authapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.logic.AuthManager;

public class RegisterActivity extends AppCompatActivity {
    private AuthManager authManager;
    private EditText etEmail, etPass, etConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authManager = new AuthManager(this);
        etEmail = findViewById(R.id.etRegEmail);
        etPass = findViewById(R.id.etRegPassword);
        etConfirm = findViewById(R.id.etRegPasswordConfirm);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnGoLogin = findViewById(R.id.btnGoLogin);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString();
            String confirm = etConfirm.getText().toString();
            btnRegister.setEnabled(false);
            authManager.register(email, pass, confirm, new AuthManager.Callback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onError(String msg) {
                    btnRegister.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnGoLogin.setOnClickListener(v -> finish());
    }
}