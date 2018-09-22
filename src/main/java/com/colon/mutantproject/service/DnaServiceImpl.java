package com.colon.mutantproject.service;

import com.colon.mutantproject.io.DnaRequest;
import com.colon.mutantproject.io.Stats;
import com.colon.mutantproject.model.Dna;
import com.colon.mutantproject.repository.DnaRepository;
import com.colon.mutantproject.service.exception.DnaBaseException;
import com.colon.mutantproject.service.exception.DnaFormatException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DnaServiceImpl implements DnaService {

  @Autowired
  private DnaRepository dnaRepository;

  /**
   * Todo: Mejorar metodo
   */
  @Override
  public Boolean isMutant(String[] dna) throws DnaFormatException, DnaBaseException {

    Map<String, Integer> baseMap = new HashMap<>();

    char[][] matrix = createMatrix(dna);
    int dim = matrix.length;

    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        String base = Character.toString(matrix[i][j]);
        if (checkBase(baseMap, base) && validateBase(base)) {

          for (int k = 0; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {

              if (checkThisMove(k, l)) {
                if (insideDna(dna, i, j, k, l)) {
                  if (matrix[i + k][j + l] == matrix[i][j]) {
                    int auxI = i + k, auxJ = j + l;
                    int count = 2;
                    while (insideDna(dna, auxI, auxJ, k, l) &&
                        (matrix[auxI + k][auxJ + l] == matrix[i][j]) && count < 4) {
                      count++;
                      auxI += k;
                      auxJ += l;
                    }
                    if (count == 4) {
                      baseMap.put(Character.toString(matrix[i][j]).toUpperCase(), count);
                    }
                  }
                }
              }
              if (mutantFound(baseMap)) {
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
    long humanCant = dnaList.size()-mutantCant;
    stats.setCountMutantDna(mutantList.size());
    stats.setCountHumanDna(humanCant);
    stats.setRatio(mutantCant/humanCant);
    return stats;
  }

  @Override
  public Long saveDna(DnaRequest dnaRequest){
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
          throw new DnaFormatException("Bad DNA format"); // throw exception
        }
        matrix[i] = dna[i].toCharArray();
      }
    }
    return matrix;
  }

  private boolean mutantFound(Map<String, Integer> baseMap) {
    if (baseMap.containsKey("A") && baseMap.containsKey("C") && baseMap.containsKey("G")) {
      return true;
    }
    return false;
  }

  /**
   * Si contiene la base {X} mutante sigo buscando las otras.
   */
  private boolean checkBase(Map<String, Integer> baseMap, String base) {
    if (baseMap.containsKey(base)) {
      return false;
    } else {
      return true;
    }
  }

  private Boolean validateBase(String base) throws DnaBaseException {
    if (!Arrays.asList("A", "C", "G", "T").contains(base)) {
      throw new DnaBaseException("Base doesn't exist!");
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
