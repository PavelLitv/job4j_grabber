package ru.job4j;

import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.service.Config;
import ru.job4j.grabber.service.SchedulerManager;
import ru.job4j.grabber.service.SuperJobGrab;
import ru.job4j.grabber.stores.JdbcStore;

public class Main {
    public static void main(String[] args) {
        var config = new Config();
        config.load("./src/main/resources/application.properties");
        var store = new JdbcStore(config);
        var post = new Post();
        post.setTitle("Super Java Job");
        post.setDescription("This job for java developer");
        post.setLink("https://example.com");
        post.setTime(System.currentTimeMillis());
        store.save(post);
        var scheduler = new SchedulerManager();
        scheduler.init();
        scheduler.load(
                Integer.parseInt(config.get("rabbit.interval")),
                SuperJobGrab.class,
                store);
    }
}
