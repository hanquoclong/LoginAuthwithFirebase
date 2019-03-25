package com.example.longhan.nfcpay;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNewpass;
    private Button btnChangePass;
    private TextView txtBack,text1;
    private FirebaseAuth auth;
    private RelativeLayout activity_reset;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initControl();
        initEvent();
        if (auth.getCurrentUser() != null)
        {
            text1.setText("Welcome ,"+auth.getCurrentUser().getEmail());
        }
    }

    private void initEvent() {
        btnChangePass.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    private void initControl() {
        edtNewpass =  (EditText) findViewById(R.id.edtNewpass);
        btnChangePass =  (Button) findViewById(R.id.btnChangePass);
        activity_reset = findViewById(R.id.activity_reset);
        txtBack =  (TextView) findViewById(R.id.txtBack);
        text1 =  (TextView) findViewById(R.id.text1);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v == btnChangePass)
        {
            changepass(edtNewpass.getText().toString());
        }
        else if (v == txtBack)
        {
            onBackPressed();
        }
    }

    private void changepass(String newPass) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPass).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Snackbar snackbar = Snackbar.make(activity_reset,"Password changed",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(activity_reset,"Error: "+task.getException(),Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
}