package shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TestDatetimeHelper {
  private static DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS", Locale.JAPAN);

  private static String TEST_DATETIME_STR = "2020-06-19 01:03:46.216000000";

  public static LocalDateTime getTestDatetime() {
    return LocalDateTime.parse(TEST_DATETIME_STR, formatter);
  }
}
