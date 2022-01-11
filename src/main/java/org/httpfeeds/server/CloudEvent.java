package org.httpfeeds.server;

import java.time.OffsetDateTime;

public class CloudEvent {

  private final String specversion;
  private final String id;
  private final String type;
  private final String source;
  private final OffsetDateTime time;
  private final String subject;
  private final String method;
  private final String datacontenttype;
  private final Object data;

  public CloudEvent(String specversion, String id, String type, String source, OffsetDateTime time,
      String subject, String method, String datacontenttype, Object data) {
    this.specversion = specversion;
    this.id = id;
    this.type = type;
    this.source = source;
    this.time = time;
    this.subject = subject;
    this.method = method;
    this.datacontenttype = datacontenttype;
    this.data = data;
  }

  public String getSpecversion() {
    return specversion;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getSource() {
    return source;
  }

  public OffsetDateTime getTime() {
    return time;
  }

  public String getSubject() {
    return subject;
  }

  public String getMethod() {
    return method;
  }

  public String getDatacontenttype() {
    return datacontenttype;
  }

  public Object getData() {
    return data;
  }
}
