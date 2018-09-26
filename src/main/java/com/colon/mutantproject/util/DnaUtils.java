package com.colon.mutantproject.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DnaUtils {
  private static final int RATIO_SCALE = 2;

  public static BigDecimal round(BigDecimal number) {
      return number.setScale(RATIO_SCALE,RoundingMode.HALF_EVEN);
  }
  
  public static boolean insideDna(char[][] dna, int posI, int posJ, int auxI, int auxJ) {
    if (posI + auxI >= dna.length || posI + auxI < 0 || posJ + auxJ >= dna.length
        || posJ + auxJ < 0) {
      return false;
    }
    return true;
  }
}
