package com.colon.mutantproject.service;

import java.util.Set;

public interface MutantValidatorService {
  
  boolean isMutant(char[][] matrix, int i, int j, int k, int l);

  public boolean mutantFound(Set<String> baseSet);
}
