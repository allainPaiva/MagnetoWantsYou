package com.paiva.allain.web;

import com.paiva.allain.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class MutantController {

    @Autowired
    private MutantService mutantService;

    public MutantController(MutantService mutantService){
        this.mutantService = mutantService;
    }
    /**
     *Endpoint for the storage of DNA sequences on data base
     * @param dna DNA sequence
     * @return ResponseEntity<Boolean> if is a mutant return 200 OK and true or if it is a human return 403 Forbidden false
     */
    @PostMapping(name = "/mutant")
    public ResponseEntity<Boolean> isMutant (@RequestBody @Valid String[] dna){
        boolean isMutant=mutantService.isMutant(dna);
        HttpStatus status = isMutant? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return new ResponseEntity<Boolean>(isMutant, status);
    }

    /**
     *Endpoint for count the amount of mutand, human from data base
     * @return Map<String,String> a map with [“count_mutant_dna”:(amount of mutant), “count_human_dna”:(amount of human): “ratio”:(average)]
     */
    @GetMapping(name = "/stats")
    public Map<String,String> getStats(){
        return mutantService.mutantStats();
    }

}
