package com.example.longhan.nfcpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtUser;
    private Button btnChangePass,btnLogout;
    private FirebaseAuth auth;
    private RelativeLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();
        initEventt();
        if (auth.getCurrentUser() != null)
        {
            txtUser.setText(auth.getCurrentUser().getEmail());
        }

    }

    private void initEventt() {
        btnChangePass.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void initControl() {
        btnChangePass =  (Button) findViewById(R.id.btnChangePass);
        btnLogout =  (Button) findViewById(R.id.btnLogout);
        activity_main = findViewById(R.id.activity_main);
        txtUser =  (TextView) findViewById(R.id.txtUser);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v == btnChangePass)
        {
            startActivity(new Intent(this,ResetPasswordActivity.class));
            finish();
        }
        else if (v == btnLogout)
        {
            logoutUser();
        }
    }

    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }
}
