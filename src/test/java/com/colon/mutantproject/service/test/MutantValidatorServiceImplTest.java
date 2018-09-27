package com.colon.mutantproject.service.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.spy;

import com.colon.mutantproject.service.MutantValidatorService;
import com.colon.mutantproject.service.MutantValidatorServiceImpl;
import com.colon.mutantproject.util.DnaBase;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MutantValidatorServiceImplTest {


  private MutantValidatorService mutantValidatorService;


  @Test
  public void testIsMutantGeneTrue() {
    String[] dnaHuman = new String[] {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    char[][] matrix = null;
    int dim = dnaHuman.length;
    if (dnaHuman != null) {
      matrix = new char[dim][dim];
      for (int i = 0; i < dim; i++) {
        matrix[i] = dnaHuman[i].toCharArray();
      }
    }
    MutantValidatorServiceImpl mutantValidator = spy(MutantValidatorServiceImpl.class);

    Boolean isMutant = mutantValidator.isMutantGene(matrix,0,4,1,0);
    assertTrue(isMutant);
  }

  @Test
  public void testIsMutantGeneFalse() {
    String[] dnaHuman = new String[] {"ATGCGA", "CAGTCC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    char[][] matrix = null;
    int dim = dnaHuman.length;
    if (dnaHuman != null) {
      matrix = new char[dim][dim];
      for (int i = 0; i < dim; i++) {
        matrix[i] = dnaHuman[i].toCharArray();
      }
    }
    MutantValidatorServiceImpl mutantValidator = spy(MutantValidatorServiceImpl.class);

    Boolean isMutant = mutantValidator.isMutantGene(matrix,0,4,1,0);
    assertFalse(isMutant);
  }

  @Test
  public void testMutantFoundTrue() {
    Set<String> baseSet = new HashSet<String>();
    baseSet.add(DnaBase.BASE_A);
    baseSet.add(DnaBase.BASE_C);
    baseSet.add(DnaBase.BASE_G);
    MutantValidatorServiceImpl mutantValidator = spy(MutantValidatorServiceImpl.class);

    Boolean isMutant = mutantValidator.mutantFound(baseSet);

    assertTrue(isMutant);
  }

  @Test
  public void testMutantFoundFalse() {
    Set<String> baseSet = new HashSet<String>();
    baseSet.add(DnaBase.BASE_A);
    baseSet.add(DnaBase.BASE_G);
    MutantValidatorServiceImpl mutantValidator = spy(MutantValidatorServiceImpl.class);

    Boolean isMutant = mutantValidator.mutantFound(baseSet);

    assertFalse(isMutant);
  }
}
