package com.airwallex.common.storage;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public enum SessionStorage {
    INSTANCE;
    private ConcurrentHashMap<String, HttpSession> sessionMap = new ConcurrentHashMap();

    public void set(String token, HttpSession session) {
        sessionMap.put(token, session);
    }

    public HttpSession remove(String token) {
        if (sessionMap.contains(token)) {
            return sessionMap.remove(token);
        }
        return null;
    }
}
