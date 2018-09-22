package com.colon.mutantproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "dna")
public class Dna {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "dna_matrix")
  private String dnaMatrix;

  @Column(name = "is_mutant", nullable = false,columnDefinition = "TINYINT")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isMutant;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDnaMatrix() {
    return dnaMatrix;
  }

  public void setDnaMatrix(String dnaMatrix) {
    this.dnaMatrix = dnaMatrix;
  }

  public Boolean getMutant() {
    return isMutant;
  }

  public void setMutant(Boolean mutant) {
    isMutant = mutant;
  }
}
