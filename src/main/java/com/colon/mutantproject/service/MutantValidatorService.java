package com.colon.mutantproject.service;

import java.util.Set;

public interface MutantValidatorService {
  
  boolean isMutantGene(char[][] matrix, int i, int j, int k, int l);

  boolean mutantFound(Set<String> baseSet);
}
