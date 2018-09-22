package com.colon.mutantproject.io;

public class DnaRequest {

  private String[] dna;
  private Boolean isMutant;

  public String[] getDna() {
    return dna;
  }

  public void setDna(String[] dna) {
    this.dna = dna;
  }

  public Boolean getMutant() {
    return isMutant;
  }

  public void setMutant(Boolean mutant) {
    isMutant = mutant;
  }
}
