package imat;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-08
 * Project: imat26
 */
public class LoginSession {

    private boolean loggedIn = false;
    private static LoginSession instance;

    private LoginSession() {

    }

    public static LoginSession getInstance() {
        if (instance == null) {
            instance = new LoginSession();
        }
        return instance;
    }

    public void setLoggedIn(boolean loggedInStatus) {
        loggedIn = loggedInStatus;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

}
