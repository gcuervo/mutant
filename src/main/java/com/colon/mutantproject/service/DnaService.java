package com.colon.mutantproject.service;

import com.colon.mutantproject.io.DnaRequest;
import com.colon.mutantproject.io.Stats;
import com.colon.mutantproject.service.exception.DnaBaseException;
import com.colon.mutantproject.service.exception.DnaFormatException;

public interface DnaService {

  Boolean isMutant(String[] dna) throws DnaFormatException, DnaBaseException;

  Stats getStats();

  Long saveDna(DnaRequest dnaRequest);
}
