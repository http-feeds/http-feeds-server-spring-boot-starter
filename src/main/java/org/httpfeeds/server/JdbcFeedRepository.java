package org.httpfeeds.server;

import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean
public class JdbcFeedRepository implements FeedRepository {

  private final JdbcTemplate jdbcTemplate;
  private final FeedItemRowMapper feedItemRowMapper;
  private final String table;

  public JdbcFeedRepository(JdbcTemplate jdbcTemplate,
      FeedItemRowMapper feedItemRowMapper,
      @Value("${httpfeed.server.table}") String table) {
    this.jdbcTemplate = jdbcTemplate;
    this.feedItemRowMapper = feedItemRowMapper;
    this.table = table;
  }

  @Override
  public List<FeedItem> findByIdGreaterThan(String lastEventId, long limit) {
    if (lastEventId == null) {
      lastEventId = "";
    }
    String sql = String.format("select * from %s where id > ? order by id limit ?", table);
    return jdbcTemplate.query(sql, feedItemRowMapper, lastEventId, limit);
  }

  @Override
  public void append(String id, String type, String source, Instant time, String subject,
      String method, String data) {
    String sql =
        String.format(
            "insert into %s (id, type, source, time, subject, method, data) values (?, ?, ?, ?, ?, ?, ?)",
            table);
    jdbcTemplate.update(sql, id, type, source, time, subject, method, data);
  }

  public void deleteAll() {
    jdbcTemplate.update(String.format("delete from %s", table));
  }
}
