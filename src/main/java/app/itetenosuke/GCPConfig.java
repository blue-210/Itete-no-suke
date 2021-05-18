package app.itetenosuke;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "gcp")
public class GCPConfig {
  private String projectId;
  private String bucketName;
}
