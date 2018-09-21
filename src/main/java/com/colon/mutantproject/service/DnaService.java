package com.colon.mutantproject.service;

import com.colon.mutantproject.service.exception.DnaBaseException;
import com.colon.mutantproject.service.exception.DnaFormatException;

public interface DnaService {

  Boolean isMutant(String[] dna) throws DnaFormatException, DnaBaseException;
}
