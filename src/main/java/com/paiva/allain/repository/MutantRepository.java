package com.paiva.allain.repository;

import com.paiva.allain.mapper.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Serializable> {
    @Override
    List<Mutant> findAll();
}
