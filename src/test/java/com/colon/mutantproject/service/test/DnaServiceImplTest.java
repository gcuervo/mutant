package com.colon.mutantproject.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import com.colon.mutantproject.io.Stats;
import com.colon.mutantproject.model.Dna;
import com.colon.mutantproject.repository.DnaRepository;
import com.colon.mutantproject.service.DnaServiceImpl;
import com.colon.mutantproject.service.MutantValidatorService;
import com.colon.mutantproject.util.DnaUtils;


@RunWith(MockitoJUnitRunner.class)
public class DnaServiceImplTest {

  @Mock
  private static DnaRepository dnaRepository;
  
  @Mock(name = "mutantValidatorService")
  private MutantValidatorService mutantValidatorService;

  @Spy
  @InjectMocks
  private static DnaServiceImpl dnaService;
  
  @Before 
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
}
  
  @Test
  public void testIsMutantTrue() {
    String[] dnaMutant = new String[] {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    doReturn(true).when(mutantValidatorService).isMutantGene(any(),anyInt(),anyInt(),anyInt(),anyInt());
    doReturn(true).when(mutantValidatorService).mutantFound(anySet());
    
    Boolean isMutant = dnaService.isMutant(dnaMutant);
    assertTrue(isMutant);
  }

  @Test
  public void testIsMutantFalse() {
    String[] dnaHuman = new String[] {"ATGCCA", "CAGTAC", "TTCTGT", "AGAAGG", "CGCCTA", "TCACTG"};
    doReturn(false).when(mutantValidatorService).isMutantGene(any(),anyInt(),anyInt(),anyInt(),anyInt());
    //doReturn(false).when(mutantValidatorService).mutantFound(anySet());

    Boolean isMutant = dnaService.isMutant(dnaHuman);
    assertFalse(isMutant);
  }



  @Test
  public void testGetStats1MOf4H() {
    List<Dna> dnaList = new ArrayList<>();
    Dna dna1 = mock(Dna.class);
    Dna dna2 = mock(Dna.class);
    Dna dna3 = mock(Dna.class);
    Dna dna4 = mock(Dna.class);
    dnaList.add(dna1);
    dnaList.add(dna2);
    dnaList.add(dna3);
    dnaList.add(dna4);

    doReturn(dnaList).when(dnaRepository).findAll();

    doReturn(true).when(dna1).getMutant();
    doReturn(false).when(dna2).getMutant();
    doReturn(false).when(dna3).getMutant();
    doReturn(false).when(dna4).getMutant();

    Stats stats = dnaService.getStats();

    assertEquals(DnaUtils.round(new BigDecimal(0.33)), stats.getRatio());
    assertEquals(3, stats.getCountHumanDna());
    assertEquals(1, stats.getCountMutantDna());
  }
}
