package org.httpfeeds.server;

import com.github.f4b6a3.uuid.UuidCreator;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class FeedAppender {

  private static final Logger log = LoggerFactory.getLogger(FeedAppender.class);

  private final FeedRepository feedRepository;

  public FeedAppender(FeedRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  public void append(
      String type, String source, Instant time, String subject, String method, Object data) {
    String id = UuidCreator.getTimeOrderedWithRandom().toString();
    String dataAsString = DataSerializer.toString(data);
    feedRepository.append(id, type, source, time, subject, method, dataAsString);
    log.debug("Appended event type={} id={}", type, id);
  }
}
