package org.httpfeeds.server;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "httpfeed.server")
public class HttpFeedServerProperties {

  private String path;
  private int limit = 1000;
  private Jdbc jdbc = new Jdbc();
  private Duration pollInterval = Duration.of(100, ChronoUnit.MILLIS);
  private List<Credential> credentials = new ArrayList<>();

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public Jdbc getJdbc() {
    return jdbc;
  }

  public void setJdbc(Jdbc jdbc) {
    this.jdbc = jdbc;
  }

  public List<Credential> getCredentials() {
    return credentials;
  }

  public void setCredentials(
      List<Credential> credentials) {
    this.credentials = credentials;
  }

  public Duration getPollInterval() {
    return pollInterval;
  }

  public void setPollInterval(Duration pollInterval) {
    this.pollInterval = pollInterval;
  }

  public static class Jdbc {

    private String table = "feed";

    public String getTable() {
      return table;
    }

    public void setTable(String table) {
      this.table = table;
    }
  }

  public static class Credential {
    private String username;
    private String password;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}