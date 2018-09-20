package com.colon.mutantproject.service;

public class MutantService {

  public Boolean isMutant(String[] dna) {
    for (int i = 0; i < dna.length; i++) {
      for (int j = 0; j < dna[i].length(); j++) {

      }
    }
  }

  private boolean checkAround(Character base, String[] dna, int posI, int posJ) {
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (insideDna(dna,posI,posJ,i,j) && i!=0 && j!=0) {
          if (dna[posI + i].toString().charAt(posJ + j) == base) {

          }
        }
      }
    }

  }

  private boolean insideDna(String[] dna, int posI, int posJ, int auxI, int auxJ) {
    if ((posI + auxI >= dna.length || posI + auxI < 0) && (posJ + auxJ >= dna.length || posJ + auxJ < 0)) {
      return false;
    }
    return true;
  }

  private Boolean checkDirection(int direction, Character base) {
    if ()
  }
}
