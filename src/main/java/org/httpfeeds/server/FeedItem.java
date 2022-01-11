package org.httpfeeds.server;

import java.time.Instant;

public class FeedItem {

  private String id;
  private String type;
  private String source;
  private Instant time;
  private String subject;
  private String method;
  private String data;

  public FeedItem(String id, String type, String source, Instant time,
      String subject, String method, String data) {
    this.id = id;
    this.type = type;
    this.source = source;
    this.time = time;
    this.subject = subject;
    this.method = method;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Instant getTime() {
    return time;
  }

  public void setTime(Instant time) {
    this.time = time;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
