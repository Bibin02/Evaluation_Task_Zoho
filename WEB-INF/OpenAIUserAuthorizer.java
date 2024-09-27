import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import database.DBOperations;

/**
 * OpenAIUserAuthorizer
 */
public class OpenAIUserAuthorizer {
    
    private static AuthorizationCallBackHandler acbh;
    private static LoginContext loginContext;

    public boolean authorize(String username, String password){
        try {
            OpenAIUserAuthorizer.acbh = new AuthorizationCallBackHandler(username, password);
            OpenAIUserAuthorizer.loginContext = new LoginContext("OpenAILoginModule", acbh);
            OpenAIUserAuthorizer.loginContext.login();
            // If above executes without exception ie, valid, then below return true works.
            return true;
        } catch (LoginException lException) {
            OpenAIUserAuthorizer.loginContext = null;
            OpenAIUserAuthorizer.acbh = null;
            System.err.println(lException.getMessage());
            return false;
        }
    }

    public boolean signOut(String APIKey){
        try {
            if (OpenAIUserAuthorizer.loginContext == null) {
                throw new LoginException("From signOut method -> logout called before login");
            }
            OpenAIUserAuthorizer.loginContext.logout();
            OpenAIUserAuthorizer.loginContext = null;
            OpenAIUserAuthorizer.acbh = null;
            return true;
        } catch (LoginException lException) {
            System.err.println(lException.getMessage());
            return false;
        }
    }

    public boolean validateAPI(String apiKey, String shortURL) {
        return new DBOperations().validateAPIDB(apiKey, shortURL);
    }
    
}