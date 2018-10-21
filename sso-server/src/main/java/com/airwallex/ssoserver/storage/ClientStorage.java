package com.airwallex.ssoserver.storage;

import java.util.*;

public enum ClientStorage {
    INSTANCE;

    private Map<String, ArrayList<String>> map = new HashMap();

    public void set(String token, String url) {
        if (map.containsKey(token)) {
            map.get(token).add(url);
        } else {
            ArrayList list = new ArrayList();
            list.add(url);
            map.put(token, list);
        }
    }
}
