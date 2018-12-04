package planificadordeturnos.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {

    private static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public static FirebaseUser getCurrentUser() {
        return currentUser;
    }
}
