package com.colon.mutantproject.io;

public class DnaMutantResponse {

  private Boolean isMutant;

  public DnaMutantResponse(Boolean isMutant) {
    this.isMutant = isMutant;
  }

  public Boolean getMutant() {
    return isMutant;
  }

  public void setMutant(Boolean mutant) {
    isMutant = mutant;
  }
}
