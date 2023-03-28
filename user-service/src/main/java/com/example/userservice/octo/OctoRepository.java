package com.example.userservice.octo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OctoRepository extends JpaRepository<Octopus,Long> {
    @Query("select m from CHARACTER_TB as m where m.userId = :userId ")
    List<Octopus> findAllOctoById(@Param("userId") Long userId);

//    @Query()
//    String updateOctoName();
//    @Query()
//    Octopus updateMainOcto();

}
