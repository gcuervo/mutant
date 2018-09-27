package com.colon.mutantproject.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.colon.mutantproject.io.DnaMutantResponse;
import com.colon.mutantproject.io.DnaRequest;
import com.colon.mutantproject.io.DnaSaveResponse;
import com.colon.mutantproject.io.Stats;
import com.colon.mutantproject.service.DnaService;

@CrossOrigin
@RestController
@RequestMapping("/mutant")
public class MutantResource {

  @Autowired
  private DnaService dnaService;

  @PostMapping
  public ResponseEntity<DnaMutantResponse> isMutant(@RequestBody DnaRequest dna) {
    Boolean isMutant = false;
    isMutant = dnaService.isMutant(dna.getDna());
    return isMutant ? ResponseEntity.status(HttpStatus.OK).body(new DnaMutantResponse(isMutant))
        : ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DnaMutantResponse(isMutant));
  }

  @GetMapping("/stats")
  public ResponseEntity<Stats> getStats() {
    Stats stats = dnaService.getStats();
    return ResponseEntity.ok().body(stats);
  }

  @PostMapping("/dna")
  public ResponseEntity<DnaSaveResponse> saveDna(@RequestBody DnaRequest dna) {
    Long idDna = dnaService.saveDna(dna);
    return ResponseEntity.status(HttpStatus.CREATED).body(new DnaSaveResponse(idDna));
  }
}
