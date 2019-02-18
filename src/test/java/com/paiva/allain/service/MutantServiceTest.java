package com.paiva.allain.service;

import com.paiva.allain.repository.MutantRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MutantServiceTest {


    private MutantRepository mutantRepository;
    private MutantService mutantService;

    @Before
    public void init (){
        //I'd mocked it in order to apply TDD
        mutantRepository = mock(MutantRepository.class);
        mutantService = new MutantService(mutantRepository);
    }

    @Test
    public void validatingIsHuman(){

        //it isn't mutant
        String [] humanDna = {
                "AATGCG",
                "TTTATG",
                "GAGAAG",
                "ACCCCT",
                "CCAGTG",
                "GTCACT"
        };

        boolean isMutant = mutantService.isMutant(humanDna);

//        verify(mutantService).isMutant(humanDna);
        assertTrue(!isMutant);

    }

    @Test
    public void validatingIsMutant(){

        //it's mutant
        String [] mutantDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        when(mutantService.saveInfo(mutantDna,false)).thenReturn(true);
        boolean is = mutantService.isMutant(mutantDna);
//        verify(mutantService).isMutant(mutantDna);
        assertTrue(mutantService.isMutant(mutantDna));
    }

    @Test
    public void validatingIsMutantWithHumanDNA(){
        //it isn't mutant
        String [] humanDna = {
                "AATGCG",
                "TTTATG",
                "GAGAAG",
                "ACCCCT",
                "CCAGTG",
                "GTCACT"
        };

        boolean isMutant = mutantService.isMutant(humanDna);

//        verify(mutantService).isMutant(humanDna);
        assertTrue(!isMutant);

    }

    @Test
    public void validatingIsHumanWithMutantDNA(){

        //it's mutant
        String [] humanDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        boolean isMutant = mutantService.isMutant(humanDna);

//        verify(mutantService).isMutant(humanDna);
        assertTrue(isMutant);

    }


    @Test
    public void validatingAllMutantCases(){

        //this is example case
        String []  baseCaseMutantDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        //this is example case with mutant dna code starting on position [0,1]
        String [] position0x1MutantDna = {
                "AATGCG",
                "CCAGTG",
                "TTTATG",
                "GAGAAG",
                "ACCCCT",
                "GTCACT"
        };
        //this is example case with mutant dna code starting on position [1,0]
        String [] position1x0MutantDna  = {
                "TCACTG",
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA"
        };
        //this is example case with mutant dna code starting on position [1,1]
        String [] position1x1MutantDna  = {
                "GTCACT",
                "AATGCG",
                "CCAGTG",
                "TTTATG",
                "GAGAAG",
                "ACCCCT"
        };

        //this is example case with the mutant sequence dna code but there isn't C nitrogenous base String
        String [] invalidMutantDna  = {
                "ACCCCT",
                "GTCACT",
                "AATGCG",
                "CCAGTG",
                "TTTATG",
                "GAGAAG"
        };

        //this is the biggest example case with 10X10 matrix and with mutant dna code starting on position [5,5]
        String [] biggestMutantDna  = {
                "CAGTGCGTAT",
                "TTTATGCTAG",
                "TCACTGTAGC",
                "CCCCTAATCG",
                "TCAGGTCACT",
                "TCAGAATGCG",//THE DIAGONAL STARTS HERE!
                "TCAGCCAGTG",
                "TCAGTTTATG",
                "TCAGGAGAAG",
                "TCAGACCCCT"
        };

        when(mutantService.saveInfo(baseCaseMutantDna,false)).thenReturn(true);

        assertTrue(mutantService.isMutant(baseCaseMutantDna));

        when(mutantService.saveInfo(position0x1MutantDna,false)).thenReturn(true);
        assertTrue(mutantService.isMutant(position0x1MutantDna));

        when(mutantService.saveInfo(position1x0MutantDna,false)).thenReturn(true);
        assertTrue(mutantService.isMutant(position1x0MutantDna));

        when(mutantService.saveInfo(position1x1MutantDna,false)).thenReturn(true);
        assertTrue(mutantService.isMutant(position1x1MutantDna));

        when(mutantService.saveInfo(invalidMutantDna,false)).thenReturn(true);
        assertTrue(!mutantService.isMutant(invalidMutantDna));

        when(mutantService.saveInfo(biggestMutantDna,false)).thenReturn(true);
        assertTrue(mutantService.isMutant(biggestMutantDna));
    }


}
