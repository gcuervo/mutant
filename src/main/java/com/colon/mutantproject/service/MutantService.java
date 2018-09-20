package com.colon.mutantproject.service;

import java.util.HashMap;
import java.util.Map;

public class MutantService {

    private Map<String, Integer> baseMap = new HashMap<>();

    public Boolean isMutant(/*String[] dn*/) {
        /*String[] dna = new String[]{"ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCAA",
                "TCACTG"};*/
        String[] dna = new String[]{"ATGCGACC",
                "CAGTGCTG",
                "TTATGTGG",
                "AGAAGGGT",
                "CCCCAAGT",
                "TCACTGGA",
                "ATGCGACA",
                "ATGCGACA"};
        char[][] matrix = null;
        int dim = dna.length;
        if (dna != null) {
            matrix = new char[dim][dim];
            for (int i = 0; i < dim; i++) {
                if (dna[i].length() != dim)
                    return false; // throw exception
                matrix[i] = dna[i].toCharArray();
            }
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
               // System.out.println("verificando: " + matrix[i][j]);
                if (checkBase(matrix, i, j)) {

                    for (int k = 0; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {


                            if (checkThisMove(k, l)) {
                                if (insideDna(dna, i, j, k, l)) {
                                   // System.out.println("verificando2: " + matrix[i + k][j + l]);
                                    if (matrix[i + k][j + l] == matrix[i][j]) {
                                        int auxI = i+k, auxJ = j+l;
                                        int count = 2;
                                        while (insideDna(dna, auxI, auxJ, k, l) && (matrix[auxI + k][auxJ + l] == matrix[i][j]) && count < 4) {
                                            count++;
                                          //  System.out.print("Letra : " + matrix[i][j] + " ");
                                            auxI += k;
                                            auxJ += l;
                                        }

                                        if (count == 4) {
                                            baseMap.put(Character.toString(matrix[i][j]).toUpperCase(), count);
                                        }
                                    }
                                }
                           }
                            if (mutantFound()) {
                                return true;
                            }

                        }
                    }
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        MutantService ms = new MutantService();

        if (ms.isMutant()) {
            System.out.println("Es mutante");
        } else {
            System.out.println("No ess mutante");
        }
    }

    private boolean mutantFound() {
        if (baseMap.containsKey("A") && baseMap.containsKey("C") && baseMap.containsKey("G")) {
            return true;
        }
        return false;
    }

    /**
     * Si contiene la base {X} mutante sigo buscando las otras.
     */
    private boolean checkBase(char[][] matrix, int i, int j) {
        if (baseMap.containsKey(Character.toString(matrix[i][j]))) {
            return false;
        } else {
            return true;
        }
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

    /**
     * La direccion se utiliza para continuar desde donde dejo de checkear y no volver a mirar los otros
     */
    private Direction checkAround(Character base, String[] dna, int posI, int posJ, Direction direction) {
        Direction dir = direction;
        for (int i = dir.getRow(); i <= 1; i++) {
            for (int j = dir.getCol(); j <= 1; j++) {
                if (insideDna(dna, posI, posJ, i, j) && i != 0 && j != 0) {
                    if (dna[posI + i].toString().charAt(posJ + j) == base) {
                        return dir;
                    }
                }
            }
        }
        return dir;
    }

    private boolean insideDna(String[] dna, int posI, int posJ, int auxI, int auxJ) {
        if (posI + auxI >= dna.length || posI + auxI < 0 || posJ + auxJ >= dna.length || posJ + auxJ < 0) {
            return false;
        }
        return true;
    }


    private class Direction {
        int row;
        int col;

        public Direction(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }
}
