package com.colon.mutantproject.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import com.colon.mutantproject.util.DnaBase;
import com.colon.mutantproject.util.DnaUtils;

@Service
public class MutantValidatorServiceImpl implements MutantValidatorService {

  @Override
  public boolean isMutantGene(char[][] matrix, int i, int j, int k, int l) {
    if (matrix[i + k][j + l] == matrix[i][j]) {
      int auxI = i + k, auxJ = j + l;
      int count = 2;
      while (DnaUtils.insideDna(matrix, auxI, auxJ, k, l)
          && (matrix[auxI + k][auxJ + l] == matrix[i][j]) && count < DnaBase.CANT_UNTIL_MUTANT) {
        count++;
        auxI += k;
        auxJ += l;
      }
      if (count == DnaBase.CANT_UNTIL_MUTANT) {
        return true;
      }
    }
    return false;
  }

  public boolean mutantFound(Set<String> baseSet) {
    if (baseSet.contains(DnaBase.BASE_A) && baseSet.contains(DnaBase.BASE_C)
        && baseSet.contains(DnaBase.BASE_G)) {
      return true;
    }
    return false;
  }

}
