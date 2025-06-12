package org.uas.util;

import java.io.*;

public class SessionManager implements Serializable {
    private static final String SESSION_FILE = "session.ser";

    private static SessionManager instance;
    private boolean isLoggedIn = false;

    private SessionManager() {
        loadSession();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            saveSession();
        }
    }

    private void loadSession() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            isLoggedIn = false;
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            SessionManager loaded = (SessionManager) ois.readObject();
            this.isLoggedIn = loaded.isLoggedIn;
        } catch (Exception e) {
            isLoggedIn = false;
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            // ignore
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void login() {
        isLoggedIn = true;
        saveSession();
    }

    public void logout() {
        isLoggedIn = false;
        saveSession();
    }
}