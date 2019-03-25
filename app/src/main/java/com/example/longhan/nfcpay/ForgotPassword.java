package com.example.longhan.nfcpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText edtForgotEmail;
    private Button btnResetPass;
    private TextView txtBack;
    private FirebaseAuth auth;
    private RelativeLayout activity_forgot;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initControl();
        initEvent();
    }

    private void initControl() {
        edtForgotEmail =  (EditText) findViewById(R.id.edtForgotEmail);
        btnResetPass =  (Button) findViewById(R.id.btnResetPass);
        activity_forgot = findViewById(R.id.activity_forgot);
        txtBack =  (TextView) findViewById(R.id.txtBack);
        auth = FirebaseAuth.getInstance();
    }

    private void initEvent() {
        btnResetPass.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnResetPass)
        {
            resetPass(edtForgotEmail.getText().toString());
        }
        else if (v == txtBack)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    private void resetPass(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot,"We have send password to email: "+email,Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot,"Failed to send password",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
    }
}
