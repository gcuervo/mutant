package com.colon.mutantproject.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import com.colon.mutantproject.util.RatioUtils;

@Service
public class DnaServiceImpl implements DnaService {

  @Autowired
  private DnaRepository dnaRepository;

  /**
   * Todo: Mejorar metodo
   *
   * Todo:Hacer que el checkeo de si es mutante sea Loose coupling
   */
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
        if (checkBase(baseSet, base) && validateBase(base)) {

          for (int k = 0; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {

              if (checkThisMove(k, l)) {
                if (insideDna(dna, i, j, k, l)) {
                  if (matrix[i + k][j + l] == matrix[i][j]) {
                    int auxI = i + k, auxJ = j + l;
                    int count = 2;
                    while (insideDna(dna, auxI, auxJ, k, l)
                        && (matrix[auxI + k][auxJ + l] == matrix[i][j]) && count < 4) {
                      count++;
                      auxI += k;
                      auxJ += l;
                    }
                    if (count == 4) {
                      baseSet.add(Character.toString(matrix[i][j]).toUpperCase());
                    }
                  }
                }
              }
              if (mutantFound(baseSet)) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public Stats getStats() {
    List<Dna> dnaList = dnaRepository.findAll();
    Stats stats = new Stats();
    List<Dna> mutantList = dnaList.stream()
                                  .filter(dna -> dna.getMutant())
                                  .collect(Collectors.toList());
    long mutantCant = mutantList.size();
    long humanCant = dnaList.size() - mutantCant;
    stats.setCountMutantDna(mutantList.size());
    stats.setCountHumanDna(humanCant);
    float ratio = humanCant != 0 ? (float) mutantCant / humanCant : mutantCant;
    stats.setRatio(RatioUtils.round(new BigDecimal(ratio)));
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

  private boolean mutantFound(Set<String> baseSet) {
    if (baseSet.contains(DnaBase.BASE_A) && baseSet.contains(DnaBase.BASE_C)
        && baseSet.contains(DnaBase.BASE_G)) {
      return true;
    }
    return false;
  }

  /**
   * Si contiene la base {X} mutante sigo buscando las otras.
   */
  private boolean checkBase(Set<String> baseSet, String base) {
    if (baseSet.contains(base)) {
      return false;
    } else {
      return true;
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
   * Veo que los movimientos a hacer sean los necesarios
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

  private boolean insideDna(String[] dna, int posI, int posJ, int auxI, int auxJ) {
    if (posI + auxI >= dna.length || posI + auxI < 0 || posJ + auxJ >= dna.length
        || posJ + auxJ < 0) {
      return false;
    }
    return true;
  }
}
