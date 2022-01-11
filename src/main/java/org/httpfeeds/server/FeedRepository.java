package org.httpfeeds.server;

import java.time.Instant;
import java.util.List;

public interface FeedRepository {

  List<FeedItem> findByIdGreaterThan(String lastEventId, long limit);

  void append(String id, String type, String source, Instant time, String subject,
      String method, String data);
}
