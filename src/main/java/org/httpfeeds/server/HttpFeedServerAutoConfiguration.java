package org.httpfeeds.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpFeedServerProperties.class)
public class HttpFeedServerAutoConfiguration {

  @Bean
  @ConditionalOnProperty(name = "httpfeed.server.path")
  HttpFeedController restFeedEndpointController(FeedFetcher feedFetcher) {
    return new HttpFeedController(feedFetcher);
  }

  @Bean
  @ConditionalOnMissingBean(FeedItemRowMapper.class)
  FeedItemRowMapper feedItemRowMapper() {
    return new FeedItemRowMapper();
  }

  @Bean
  @ConditionalOnClass(JdbcTemplate.class)
  @ConditionalOnMissingBean(FeedRepository.class)
  FeedRepository feedRepository(
      JdbcTemplate jdbcTemplate,
      FeedItemRowMapper feedItemRowMapper,
      HttpFeedServerProperties properties) {
    return new JdbcFeedRepository(
        jdbcTemplate, feedItemRowMapper, properties.getJdbc().getTable());
  }

  @Bean
  @ConditionalOnMissingBean(FeedFetcher.class)
  FeedFetcher feedFetcher(FeedRepository feedRepository, HttpFeedServerProperties properties) {
    return new FeedFetcher(feedRepository, properties.getPollInterval(), properties.getLimit());
  }

  @Bean
  @ConditionalOnMissingBean(FeedAppender.class)
  FeedAppender feedAppender(FeedRepository feedRepository) {
    return new FeedAppender(feedRepository);
  }

//  @Configuration(proxyBeanMethods = false)
//  @ConditionalOnClass(InMemoryUserDetailsManager.class)
//  public static class RestFeedServerSecurityAutoConfiguration {
//
//    @Bean
//    @ConditionalOnProperty(name = "httpfeed.server.credentials[0].username")
//    UserDetailsService userDetailsService(HttpFeedServerProperties properties) {
//      List<UserDetails> users =
//          properties.getCredentials().stream()
//              .map(
//                  credential ->
//                      new User(
//                          credential.getUsername(),
//                          credential.getPassword(),
//                          Collections.singletonList(new SimpleGrantedAuthority("USER"))))
//              .collect(Collectors.toList());
//      return new InMemoryUserDetailsManager(users);
//    }
//  }
}
