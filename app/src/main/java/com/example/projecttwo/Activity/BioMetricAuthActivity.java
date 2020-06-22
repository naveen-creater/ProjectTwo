package com.example.projecttwo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttwo.MainActivity;
import com.example.projecttwo.R;

import java.util.concurrent.Executor;

public class BioMetricAuthActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Button biometricLoginButton;
    private TextView authState;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric_auth);
        initView(); // initView

        listeners(); //click liteners

        bioMetricAuth();

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Scan your fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

    }

    private void bioMetricAuth() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(BioMetricAuthActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Toast.makeText(BioMetricAuthActivity.this, "Authentication"+errString, Toast.LENGTH_SHORT).show();

                authState.setText("Authendication"+errString);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(BioMetricAuthActivity.this,"Authentication success", Toast.LENGTH_SHORT).show();
                authState.setText("Authendication Success");
                startActivity(new Intent(BioMetricAuthActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(BioMetricAuthActivity.this,"Authentication failed, please try again",Toast.LENGTH_SHORT).show();

                authState.setText("athuendication Failed, please Try agian");

            }
        });
    }

    private void listeners() {
        biometricLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BioMetricAuthActivity.this,MainActivity.class));
            }
        });
    }

    private void initView() {
        biometricLoginButton = findViewById(R.id.biometri);
        authState = findViewById(R.id.bioState);
        login = findViewById(R.id.normallogin);
    }
}