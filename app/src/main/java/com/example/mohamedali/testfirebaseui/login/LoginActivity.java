package com.example.mohamedali.testfirebaseui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamedali.testfirebaseui.R;
import com.example.mohamedali.testfirebaseui.login.model.User;
import com.example.mohamedali.testfirebaseui.main.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final int REQUEST_IMAGE_OPEN = 155;

    private StorageReference mUserProfilePhotoStorageReference;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

    @BindView(R.id.bt_continue)
    Button mContinueButton;

    @BindView(R.id.img_account_profile)
    CircleImageView mCircleImageView;

    @BindView(R.id.fab_update_profile_photo)
    FloatingActionButton mUpdateProfilePhotoFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUserProfilePhotoStorageReference = FirebaseStorage.getInstance().getReference().child(User.USERS_PHOTO);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("loading");
        mProgressDialog.setCancelable(true);

        mUpdateProfilePhotoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), REQUEST_IMAGE_OPEN);
            }
        });

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    // Save user in database
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            signIn();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(this, "Sign in successfully " + user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        } else if (requestCode == REQUEST_IMAGE_OPEN) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {
                    showProgressDialog();
                    if (mAuth != null) {
                        StorageReference photoRef = mUserProfilePhotoStorageReference.child(mAuth.getCurrentUser().getUid());


                        photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUri = taskSnapshot.getUploadSessionUri();
                                Picasso.get().load(downloadUri).into(mCircleImageView);
                                hideProgressDialog();
                            }
                        });


                    }
                }
            }
        }

    }

    private void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    private void showProgressDialog() {
        mProgressDialog.show();
    }
}
