package com.async.service;


import com.async.dto.Search;
import com.async.dto.User;

import java.util.concurrent.CompletableFuture;

public interface GitHubLookupService {
    CompletableFuture<User> findUser(String user);
    CompletableFuture<Search> findUsers(String query, int page, int offset);
}
