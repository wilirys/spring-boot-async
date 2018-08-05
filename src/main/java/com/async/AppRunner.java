package com.async;

import com.async.dto.Item;
import com.async.dto.Search;
import com.async.dto.User;
import com.async.service.GitHubLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner {

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<Search> searchResult = gitHubLookupService.findUsers("A", 1, 25);
        List<Item> users = searchResult.join().getItems();
        while(!users.isEmpty()) {
            users.forEach(u -> {
                CompletableFuture<User> user = gitHubLookupService.findUser(u.getLogin());
            });
        }

        CompletableFuture<User> user1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> user2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> user3 = gitHubLookupService.findUser("Spring-Projects");

        CompletableFuture.allOf(user1, user2, user3).join();

        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + user1.get());
        log.info("--> " + user2.get());
        log.info("--> " + user3.get());
    }
}
