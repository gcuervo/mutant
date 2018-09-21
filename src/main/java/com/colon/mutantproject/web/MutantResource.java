package com.colon.mutantproject.web;


import com.colon.mutantproject.io.DnaRequest;
import com.colon.mutantproject.service.DnaService;
import com.colon.mutantproject.service.exception.DnaFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mutant")
public class MutantResource {

  private Logger logger = LoggerFactory.getLogger(MutantResource.class);

  @Autowired
  private DnaService dnaService;

  @PostMapping
  public ResponseEntity isMutant(@RequestBody DnaRequest dna) {
    Boolean isMutant = false;
    try {
      isMutant = dnaService.isMutant(dna.getDna());
    } catch (DnaFormatException e) {
      logger.error("Error: " + e.getMessage(),e);
    }
    return isMutant ? new ResponseEntity(HttpStatus.OK)
        : new ResponseEntity(HttpStatus.FORBIDDEN);
  }
}
