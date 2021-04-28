package app.itetenosuke.api.domain.painrecord;

public enum PainLevel {
  NO_PAIN(0, "痛くない", "no-pain"), MODERATE(1, "ちょっと痛い", "moderate"), VERY_SEVERE_PAIN(2, "そこそこ痛い",
      "very-severe-pain"), WORST_PAIN_POSSIBLE(3, "かなり痛い", "worst-pain-possible");

  private final int code;
  private final String name;
  private final String cssClass;

  private PainLevel(int code, String name, String cssClass) {
    this.code = code;
    this.name = name;
    this.cssClass = cssClass;
  }

  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getCssClass() {
    return cssClass;
  }
}
