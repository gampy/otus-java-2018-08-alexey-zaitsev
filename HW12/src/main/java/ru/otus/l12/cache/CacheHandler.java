package ru.otus.l12.cache;

import ru.otus.l12.ORM.DBService;
import ru.otus.l12.dataSets.UserDataSet;

import java.util.List;
import java.util.Map;

public class CacheHandler {
    private static CacheEngine<Long, Object> cache;

    public CacheHandler() {
        cache = new CacheEngineImpl<>(100, 0, 0, false);
    }

    public void pushUsersToCache(DBService dbService) {
        List<UserDataSet> users = dbService.readAll();
        for (UserDataSet user : users) {
            cache.put(user.getId(), user.getLogin());
        }
    }

    public Map<Long, Object> getCachedUsers() {
        return cache.getAll();
    }

}
