package com.example.longhan.nfcpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUp;
    private EditText edtEmail, edtPass;
    private TextView txtForgot,txtSignIn;
    private RelativeLayout activity_signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initControl();
        initEvent();
    }

    private void initEvent() {
        btnSignUp.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);
        txtForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSignIn)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v == btnSignUp)
        {
            registerUser();
        }
        else if (v == txtForgot)
        {
            startActivity(new Intent(SignupActivity.this,ResetPasswordActivity.class));
            finish();
        }
    }
    private void initControl() {
        auth = FirebaseAuth.getInstance();
        activity_signup = findViewById(R.id.activity_signup);
        progressDialog = new ProgressDialog(this);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        txtSignIn = findViewById(R.id.txtSignIn);
        txtForgot = findViewById(R.id.txtForgot);

    }
    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
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
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()){
                            snackbar = Snackbar.make(activity_signup,"Error: "+task.getException(),Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else
                        {
                            snackbar = Snackbar.make(activity_signup,"Register Success",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
    }
}
