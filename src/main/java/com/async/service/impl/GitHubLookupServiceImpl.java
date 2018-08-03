package com.async.service.impl;

import com.async.dto.User;
import com.async.service.GitHubLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GitHubLookupServiceImpl implements GitHubLookupService {

    private final RestTemplate restTemplate;

    public GitHubLookupServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(results);
    }
}
