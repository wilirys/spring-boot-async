package com.async.service;


import com.async.dto.User;

import java.util.concurrent.CompletableFuture;

public interface GitHubLookupService {
    CompletableFuture<User> findUser(String user) throws InterruptedException;
}
