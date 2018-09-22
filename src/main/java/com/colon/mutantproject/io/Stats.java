package com.colon.mutantproject.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "countMutantDna", "countHumanDna", "ratio"})
public class Stats {

  private long countMutantDna;
  private long countHumanDna;
  private float ratio;

  @JsonProperty("count_mutant_dna")
  public long getCountMutantDna() {
    return countMutantDna;
  }

  public void setCountMutantDna(long countMutantDna) {
    this.countMutantDna = countMutantDna;
  }

  @JsonProperty("count_human_dna")
  public long getCountHumanDna() {
    return countHumanDna;
  }

  public void setCountHumanDna(long countHumanDna) {
    this.countHumanDna = countHumanDna;
  }

  public float getRatio() {
    return ratio;
  }

  public void setRatio(float ratio) {
    this.ratio = ratio;
  }
}
