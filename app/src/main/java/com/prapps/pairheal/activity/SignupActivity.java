package com.prapps.pairheal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.prapps.pairheal.R;
import com.prapps.pairheal.utils.AppConstants;
import com.prapps.pairheal.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    EditText mFirstName;
    @BindView(R.id.et_last_name)
    EditText mLastName;
    @BindView(R.id.et_email)
    EditText mEmail;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @OnClick(R.id.signup)
    void join() {
        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success");
                        //FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        updateUserProfile();
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user.
                        // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        if (task.getException() != null)
                            Toast.makeText(SignupActivity.this, "Authentication failed due to " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });

    }

    private void updateUserProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mFirstName.getText().toString() + " " + mLastName.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        gotoHome();
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user.
                        // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        if (task.getException() != null)
                            Toast.makeText(SignupActivity.this, "User profile update failed due to " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
        gotoHome();

    }

    private void gotoHome() {
        PreferenceManager.getInstance(this).put(AppConstants.KEY_EMAIL, mEmail.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_PASS, mPassword.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_FIRST_NAME, mFirstName.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_LAST_NAME, mLastName.getText().toString());
        PreferenceManager.getInstance(this).put(AppConstants.KEY_LOGGED_IN, true);
        startActivity(new Intent(this, HomeActivity.class));
        SignupActivity.this.finish();
    }

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Join PairHeal");
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
