package com.prapps.pairheal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prapps.pairheal.R;
import com.prapps.pairheal.utils.AppConstants;
import com.prapps.pairheal.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText mUsername;
    @BindView(R.id.et_password)
    EditText mPassword;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    FirebaseAuth mAuth;

    @OnClick(R.id.button_login)
    public void login() {
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mUsername.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            gotoHomeAfterSave();
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if (task.getException() != null)
                                Toast.makeText(LoginActivity.this, "Authentication failed due to " + task.getException().getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                            //updateUI(null);
                        }
                    }
                });


    }

    private void gotoHomeAfterSave() {
        PreferenceManager.getInstance(this).put(AppConstants.KEY_EMAIL, mUsername.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_PASS, mPassword.getText().toString());
//        PreferenceManager.getInstance(this).put(AppConstants.KEY_FIRST_NAME, mFirstName.getText().toString());
//        PreferenceManager.getInstance(this).put(AppConstants.KEY_LAST_NAME, mLastName.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_LOGGED_IN, true);
        startActivity(new Intent(this, HomeActivity.class));
        LoginActivity.this.finish();

    }

    @OnClick(R.id.button_signup)
    public void gotoSignup() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
    }
}
