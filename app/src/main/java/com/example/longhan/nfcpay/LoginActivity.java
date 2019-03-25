package com.example.longhan.nfcpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn;
    private EditText edtEmail, edtPass;
    private TextView txtForgot, txtSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControl();
        initEventt();
        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtSignUp)
        {
            Intent intent = new Intent(this,SignupActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v == btnSignIn)
        {
            signIn();
        }
        else if (v == txtForgot)
        {
            Intent intent = new Intent(this,ForgotPassword.class);
            startActivity(intent);
            finish();
        }
    }
    private void initEventt() {
        btnSignIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgot.setOnClickListener(this);
    }

    private void initControl() {
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnSignIn = findViewById(R.id.btnSignIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        txtForgot = findViewById(R.id.txtForgot);
        txtSignUp = findViewById(R.id.txtSignUp);
    }
    private void signIn() {
        String email = edtEmail.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()){
                            if (pass.length() <6)
                            {
                                Toast.makeText(LoginActivity.this, "Password length must be over 6", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(LoginActivity.this,"Error: "+task.getException(),Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }
                });
    }
}
