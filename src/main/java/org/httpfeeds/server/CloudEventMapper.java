package org.httpfeeds.server;

import java.time.ZoneOffset;

public class CloudEventMapper {
  public static CloudEvent toCloudEvent(FeedItem feedItem) {
    return new CloudEvent(
        "1.0",
        feedItem.getId(),
        feedItem.getType(),
        feedItem.getSource(),
        feedItem.getTime().atOffset(ZoneOffset.UTC),
        feedItem.getSubject(),
        null,
        "application/json",
        DataSerializer.toObject(feedItem.getData()));
  }
}
