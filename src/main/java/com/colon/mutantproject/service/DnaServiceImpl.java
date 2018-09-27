package com.colon.mutantproject.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.colon.mutantproject.io.DnaRequest;
import com.colon.mutantproject.io.Stats;
import com.colon.mutantproject.model.Dna;
import com.colon.mutantproject.repository.DnaRepository;
import com.colon.mutantproject.service.exception.DnaBaseException;
import com.colon.mutantproject.service.exception.DnaFormatException;
import com.colon.mutantproject.service.exception.DnaNotExistException;
import com.colon.mutantproject.util.DnaBase;
import com.colon.mutantproject.util.DnaUtils;

@Service
public class DnaServiceImpl implements DnaService {
  private static final Logger logger = LoggerFactory.getLogger(DnaServiceImpl.class);

  @Autowired
  private DnaRepository dnaRepository;

  @Autowired
  private MutantValidatorService mutantValidatorService;

  DnaServiceImpl(DnaRepository dnaRepository, MutantValidatorService mutantValidatorService) {
    this.dnaRepository = dnaRepository;
    this.mutantValidatorService = mutantValidatorService;
  }

  @Override
  public Boolean isMutant(String[] dna) throws DnaFormatException, DnaBaseException {
    if (dna == null) {
      throw new DnaNotExistException("DNA don't exist");
    }
    Set<String> baseSet = new HashSet<>();
    char[][] matrix = createMatrix(dna);
    int dim = matrix.length;
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        String base = Character.toString(matrix[i][j]);
        if (!alreadyInMutantBase(baseSet, base) && validateBase(base)) {
          if (checkAround(baseSet, matrix, i, j)) {
            if (mutantValidatorService.mutantFound(baseSet)) {
              logger.info("******* Mutant Found *******");
              return true;
            }
          }
        }
      }
    }
    logger.info("******* Mutant not Found *******");
    return false;
  }

  private boolean checkAround(Set<String> baseSet, char[][] matrix, int i, int j) {
    boolean mutantGeneFound = false;
    for (int k = 0; k <= 1; k++) {
      for (int l = -1; l <= 1; l++) {
        if (checkThisMove(k, l) && DnaUtils.insideDna(matrix, i, j, k, l)) {
         // if (DnaUtils.insideDna(matrix, i, j, k, l)) {
            if (mutantValidatorService.isMutantGene(matrix, i, j, k, l)) {
              logger.info(String.format("Mutant gene found -> %s", matrix[i][j]));
              baseSet.add(Character.toString(matrix[i][j]).toUpperCase());
              return true;
            }
          //}
        }
      }
    }
    return mutantGeneFound;
  }

  @Override
  public Stats getStats() {
    List<Dna> dnaList = dnaRepository.findAll();
    Stats stats = new Stats();
    List<Dna> mutantList =
        dnaList.stream().filter(dna -> dna.getMutant()).collect(Collectors.toList());
    long mutantCant = mutantList.size();
    long humanCant = dnaList.size() - mutantCant;
    stats.setCountMutantDna(mutantList.size());
    stats.setCountHumanDna(humanCant);
    float ratio = humanCant != 0 ? (float) mutantCant / humanCant : mutantCant;
    stats.setRatio(DnaUtils.round(new BigDecimal(ratio)));
    return stats;
  }

  @Override
  public Long saveDna(DnaRequest dnaRequest) {
    Dna dna = new Dna();
    dna.setDnaMatrix(Arrays.toString(dnaRequest.getDna()));
    dna.setMutant(dnaRequest.getMutant());
    return dnaRepository.save(dna).getId();
  }

  private char[][] createMatrix(String[] dna) throws DnaFormatException {
    char[][] matrix = null;
    int dim = dna.length;
    if (dna != null) {
      matrix = new char[dim][dim];
      for (int i = 0; i < dim; i++) {
        if (dna[i].length() != dim) {
          throw new DnaFormatException("Bad DNA matrix format");
        }
        matrix[i] = dna[i].toCharArray();
      }
    }
    return matrix;
  }


  /**
   * If it contains the {X} mutant base I keep looking for the others.
   */
  private boolean alreadyInMutantBase(Set<String> baseSet, String base) {
    if (baseSet.contains(base)) {
      return true;
    } else {
      return false;
    }
  }


  private Boolean validateBase(String base) throws DnaBaseException {
    if (!Arrays.asList(DnaBase.BASE_A, DnaBase.BASE_C, DnaBase.BASE_G, DnaBase.BASE_T)
        .contains(base)) {
      throw new DnaBaseException(String.format("Base %s doesn't exist", base));
    }
    return true;
  }

  /**
   * Do only Necessary movements
   */
  private boolean checkThisMove(int rowMove, int colMove) {
    if (rowMove == 0 && colMove == 1) {
      return true;
    } else if (rowMove == 1 && colMove == -1) {
      return true;
    } else if (rowMove == 1 && colMove == 0) {
      return true;
    } else if (rowMove == 1 && colMove == 1) {
      return true;
    }
    return false;
  }
}
