package planificadordeturnos.views;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import planificadordeturnos.models.User;
import planificadordeturnos.util.CurrentUser;

public class LoginActivity extends AppCompatActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Request code value
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if( CurrentUser.getCurrentUser() != null){
            logged();
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

        final User user = new User();
        user.setId(CurrentUser.getCurrentUser().getUid());
        user.setName(CurrentUser.getCurrentUser().getDisplayName());
        user.setProfile("Candidato");

        DatabaseReference mPostReference = database.getReference("usuarios").child(user.getId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User userDatabase = dataSnapshot.getValue(User.class);
                if(userDatabase == null){
                    DatabaseReference mPostReference2 = database.getReference("usuarios").child(user.getId());
                }

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mPostReference.addValueEventListener(postListener);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}