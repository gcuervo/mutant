package com.colon.mutantproject.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RatioUtils {
  private static final int RATIO_SCALE = 2;

  public static BigDecimal round(BigDecimal number) {
      return number.setScale(RATIO_SCALE,RoundingMode.HALF_EVEN);
  }
}
