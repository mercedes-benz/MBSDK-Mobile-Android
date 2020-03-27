package com.daimler.mbingresskit;

import com.daimler.mbingresskit.login.LoginActionHandler;
import com.daimler.mbingresskit.login.LoginProcess;
import com.daimler.mbingresskit.login.UserCredentialsLoginState;

import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class UserCredentialsLoginTest implements LoginActionHandler {

    private static final String ACTION_REQUEST_TOKEN = "R";
    private static final String ACTION_FINISH_LOGIN = "F";
    private static final String ACTION_LOGOUT = "O";

    private LoginProcess loginProcess;

    private String actions = "";

    @Before
    public void setup() {
        loginProcess = new LoginProcess(this, UserCredentialsLoginState.LoggedOut.INSTANCE);
        actions = "";
    }

    private void assertActions(String expected) {
        assertEquals(expected, actions);
    }

    // todo: add tests

    @Override
    public void authorize() {
        // should not be called in this login workflow
    }

    @Override
    public void requestToken() {
        actions += ACTION_REQUEST_TOKEN;
    }

    @Override
    public void finishLogin() {
        actions += ACTION_FINISH_LOGIN;
    }

    @Override
    public void finishLogout() {
        actions += ACTION_LOGOUT;
    }
}
