package planificadordeturnos.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import planificadordeturnos.models.User;

public class LoginActivity extends AppCompatActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Request code value
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }else{
            signIn();
        }
    }

    private void signIn(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()/*,
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build(),
                                new AuthUI.IdpConfig.GitHubBuilder().build(),

                                new AuthUI.IdpConfig.PhoneBuilder().build()*/)).setIsSmartLockEnabled(false)
                        //.setTheme(R.style.LoginTheme)
                        //.setLogo(R.mipmap.logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                logged();
            } else {

            }
        }
    }

    private void logged(){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Save user in firebase database
        DatabaseReference mPostReference = database.getReference("users").child(firebaseUser.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Save in Shared Preferences
                SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("plannerApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                // Get Post object and use the values to update the UI
                User userDatabase = dataSnapshot.getValue(User.class);
                if(userDatabase == null){
                    final User user = new User();
                    user.setId(firebaseUser.getUid());
                    user.setName(firebaseUser.getDisplayName());
                    user.setProfile("Candidato");

                    editor.putString("Profile", user.getProfile());
                    database.getReference("users").child(user.getId()).setValue(user);
                }else{
                    editor.putString("Profile", userDatabase.getProfile());
                }

                editor.apply();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mPostReference.addValueEventListener(postListener);


    }
}