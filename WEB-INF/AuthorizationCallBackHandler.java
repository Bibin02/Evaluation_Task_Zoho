import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class AuthorizationCallBackHandler implements CallbackHandler{
    String uname = null;
    String pass = null;
    public AuthorizationCallBackHandler(String username, String password){
        this.uname = username;
        this.pass = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        int n = callbacks.length;
        for (int counter = 0; counter < n; counter++) {
            if (callbacks[counter] instanceof NameCallback) {
                NameCallback ncbk = (NameCallback) callbacks[counter];
                ncbk.setName(uname);
            }
            if (callbacks[counter] instanceof PasswordCallback) {
                PasswordCallback pcbk = (PasswordCallback) callbacks[counter];
                pcbk.setPassword(pass.toCharArray());
            }
        }
    }
    
}
