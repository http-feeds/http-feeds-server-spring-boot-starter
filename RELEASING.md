# Releasing

Have a ~/.mw2/settings.xml

```
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${Sonatype JIRA username}</username>
      <password>${Sonatype JIRA username}</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.keyname>${GPG Key name}</gpg.keyname>
      </properties>
    </profile>
  </profiles>
</settings>
```

Releasing
```
./mvnw release:clean release:prepare
./mvnw release:perform
```