package com.async.service.impl;

import com.async.dto.Search;
import com.async.dto.User;
import com.async.service.GitHubLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GitHubLookupServiceImpl implements GitHubLookupService {

    private final RestTemplate restTemplate;

    public GitHubLookupServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<User> findUser(String user) {
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        return CompletableFuture.completedFuture(results);
    }

    @Async
    public CompletableFuture<Search> findUsers(String query, int page, int offset) {
        log.info("Search users by query{" + query + "}");

        Map<String, Object> params = new HashMap<>();
        params.put("q", query);
        params.put("page", page);
        params.put("per_page", offset);
        Search results = restTemplate.getForObject("https://api.github.com/search/users", Search.class, params);

        return CompletableFuture.completedFuture(results);
    }
}
