package org.httpfeeds.server;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${httpfeed.server.path}")
public class HttpFeedController {

  private static final Logger log = LoggerFactory.getLogger(HttpFeedController.class);
  private final FeedFetcher feedFetcher;

  public HttpFeedController(FeedFetcher feedFetcher) {
    this.feedFetcher = feedFetcher;
  }

  @GetMapping(produces = "application/cloudevents-batch+json")
  public List<CloudEvent> getFeedItems(
      @RequestParam(name = "lastEventId", required = false) String lastEventId,
      @RequestParam(name = "timeout", required = false) Long timeoutMillis) {
    log.debug("GET feed with lastEventId {}", lastEventId);

    List<FeedItem> items;
    if (timeoutMillis == null) {
      items = feedFetcher.fetch(lastEventId);
    } else {
      items = feedFetcher.fetchWithPolling(lastEventId, timeoutMillis);
    }

    List<CloudEvent> cloudEvents =
        items.stream().map(CloudEventMapper::toCloudEvent).collect(Collectors.toList());

    log.debug("GET feed with lastEventId {} returned {} events", lastEventId, cloudEvents.size());
    return cloudEvents;
  }
}
