package app.itetenosuke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IteteNoSukeApplication {

  public static void main(String[] args) {
    SpringApplication.run(IteteNoSukeApplication.class, args);
  }
}
