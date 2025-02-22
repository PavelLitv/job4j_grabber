package ru.job4j.grabber.stores;

import org.apache.log4j.Logger;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.service.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStore implements Store {
    private static final Logger LOG = Logger.getLogger(JdbcStore.class);
    private Connection connection;

    public JdbcStore(Config config) {
        init(config);
    }

    private void init(Config config) {
        try {
            Class.forName(config.get("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.get("url"),
                    config.get("username"),
                    config.get("password"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO post(name, text, link, created) VALUES(?, ?, ?, ?)")) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, new Timestamp(post.getTime()));
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM post");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                posts.add(
                        new Post(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getTimestamp(5).getTime()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Optional<Post> findById(Long id) {
        Optional<Post> post = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = Optional.of(new Post(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(5).getTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
