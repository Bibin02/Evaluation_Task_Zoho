import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import database.DBOperations;

public class OpenAILoginModule implements LoginModule{
    CallbackHandler callbackHandler;
    boolean authorizationSuccessFlag = false;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
            Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbackArray = new Callback[2];
        callbackArray[0] = new NameCallback("UserName : ");
        callbackArray[1] = new PasswordCallback("Password : ", false);

        try {
            callbackHandler.handle(callbackArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String username = ((NameCallback)callbackArray[0]).getName();
        String password = new String(((PasswordCallback)callbackArray[1]).getPassword());

        // Main logic
        if (new DBOperations().checkUser(username, password)) {
            authorizationSuccessFlag = true;
        }
        else{
            authorizationSuccessFlag = false;
            throw new FailedLoginException("Wrong User Credentials...");
        }

        return authorizationSuccessFlag;
    }

    @Override
    public boolean commit() throws LoginException {
        return authorizationSuccessFlag;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
