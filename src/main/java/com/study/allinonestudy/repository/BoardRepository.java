package com.study.allinonestudy.repository;


import com.study.allinonestudy.entity.Board;
import com.study.allinonestudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

}
