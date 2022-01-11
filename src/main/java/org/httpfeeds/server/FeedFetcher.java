package org.httpfeeds.server;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class FeedFetcher {

  private static final Logger log = LoggerFactory.getLogger(FeedFetcher.class);

  private final FeedRepository feedRepository;
  private final Duration pollInterval;
  private final long limit;

  public FeedFetcher(FeedRepository feedRepository, Duration pollInterval, long limit) {
    this.feedRepository = feedRepository;
    this.pollInterval = pollInterval;
    this.limit = limit;
  }

  public List<FeedItem> fetch(String lastEventId) {
    log.debug("Find items with lastEventId={}", lastEventId);
    return feedRepository.findByIdGreaterThan(lastEventId, limit);
  }

  public List<FeedItem> fetchWithPolling(String lastEventId, Long timeoutMillis) {
    log.debug("Long polling for items with lastEventId={} timeoutMillis={}", lastEventId, timeoutMillis);
    Instant timeoutTimestamp = Instant.now().plus(timeoutMillis, MILLIS);
    List<FeedItem> items;
    while (true) {
      items = feedRepository.findByIdGreaterThan(lastEventId, limit);

      int numberOfItems = items.size();
      if (numberOfItems > 0) {
        log.debug("Returning {} items.", numberOfItems);
        return items;
      }

      if (Instant.now().isAfter(timeoutTimestamp)) {
        log.debug("Polling timed out. Returning the empty response.");
        return items;
      }

      try {
        log.debug("No items found. Wait {} and then retry again.", pollInterval);
        //noinspection BusyWait
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException e) {
        log.debug("Thread was interrupted. Probably a graceful shutdown. Try to send response.");
        return items;
      }
    }
  }

}
