package com.paiva.allain.service;

import com.paiva.allain.mapper.Mutant;
import com.paiva.allain.repository.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MutantService{

    @Autowired
    private MutantRepository mutantRepository;

    public MutantService (){

    }

    public MutantService (MutantRepository mutantRepository){
        this.mutantRepository = mutantRepository;
    }

    /**
     * method that verify if a dna sequence belongs to a mutant
     * @param dna dna sequence
     * @return boolean true = mutant, false = humanx
     */
    public boolean isMutant(String[] dna){

        boolean isMutant= false;

        //split the dna String array to a multidimensional array, just for commodity
        String [][] fixedDna = new String [dna.length][dna.length];
        for (int i = 0; i < dna.length; i++) {
            fixedDna [i]=dna[i].split("");
        }

        if(validateDNA(fixedDna)){
            int validDnaMatrix = dna.length-5;
            int [] position = null;
            int row =0;
            int column =0;
            search: for (; row<=validDnaMatrix; row++){
                column=0;
                for (; column<=validDnaMatrix; column++){
                    position=findDiagonal(fixedDna, row,column,0);
                    if(position!=null) break search;
                }
            }

            //possible mutant
            if (position!=null && position[0]!=-1){
                //looking for C nitrogenous base row
                List<String> cColumn= Arrays.asList(fixedDna[position[0]]).subList(column,position[1]);
                boolean isCMutant = cColumn.stream().allMatch(a -> a.equalsIgnoreCase("C"));

                //looking for G nitrogenous base row
                List<String> gColumn= new ArrayList();
                for (int i =row; i <= position[0]-1; i++){
                    gColumn.add(fixedDna [i][position[1]]);
                }

                boolean isGMutant = gColumn.stream().allMatch(a -> a.equalsIgnoreCase("G"));

                //it is mutant
                isMutant = isCMutant && isGMutant;
            }
        }
        saveInfo(dna,isMutant);
        //it is human
        return isMutant;
    }

    /**
     * validate dna structure
     * @param dna array with dna sequence
     * @return boolean true valid sequence
     * */
    private boolean validateDNA(String[][] dna){
        boolean isValid = true;
        int validLength = dna.length;
        //validating DNA array minimum length
        if (validLength>=5){
            //validating DNA square matrix
            for (int i = 0; i >dna.length; i++){
                if (dna[i].length!=validLength){
                    isValid = false;
                    break;
                }
            }
        }else {
            isValid = false;
        }
        return isValid;
    }

    /**
     * recursive method which find diagonal mutant sequence (consecutive 4 A)
     * @param dna array with dna sequence
     * @param vInit vertical position to init to search
     * @param hInit horizontal position to init to search
     * @param occurrence occurrences of tuple (AA)
     * */
    private int[] findDiagonal(String[][] dna, int vInit, int hInit, int occurrence){

        String firstPosition = dna[vInit][hInit];
        int [] position = {-1,-1};

        if (occurrence==3){
            position[0]= dna.length>vInit+1?vInit+1:-1;
            position[1]= dna.length>hInit+1?hInit+1:-1;
            //is a possible mutant
            return position;
        }

        if (firstPosition.equalsIgnoreCase("A") && firstPosition.equalsIgnoreCase(dna[vInit + 1][hInit + 1])) {
            position = findDiagonal(dna, vInit + 1, hInit + 1, occurrence+1);
        }

        //is a no-mutant
        return position[0]!=-1? position : null;
    }

    /**
     * Storage dna sequence on database
     * @param dna array dna sequence
     * @param isMutant it indicate if dna sequence belongs to a mutant
     * */
    public boolean saveInfo(String [] dna, boolean isMutant){
        mutantRepository.save(new Mutant(0,Arrays.asList(dna).toString(),isMutant));
        return true;
    }

    /**
     * Count the amount of mutand, human from data base
     * @return Map<String,String> a map with [“count_mutant_dna”:(amount of mutant), “count_human_dna”:(amount of human): “ratio”:(average)]
     * */
    public Map<String, String> mutantStats(){
        List<Mutant> mutants = mutantRepository.findAll();

        Long totalMutants=mutants.stream().filter(mutant -> mutant.isMutant()).count();
        Long totalHuman=mutants.stream().filter(mutant -> !mutant.isMutant()).count();
        Double mutantAverage =  mutants.size()>0? new Double(totalMutants)/mutants.size():0;

        Map<String,String> stats = new HashMap<>();
        stats.put("count_mutant_dna",totalMutants.toString());
        stats.put("count_human_dna",totalHuman.toString());
        stats.put("ratio",mutantAverage.toString());
        return stats;
    }


}
