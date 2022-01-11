package org.httpfeeds.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class FeedItemRowMapper implements RowMapper<FeedItem> {

  @Override
  public FeedItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    String id = rs.getString("id");
    String type = rs.getString("type");
    String source = rs.getString("source");
    Timestamp time = rs.getTimestamp("time");
    String subject = rs.getString("subject");
    String method = rs.getString("method");
    String data = rs.getString("data");
    return new FeedItem(id, type, source, time == null ? null : time.toInstant(), subject, method, data);
  }
}