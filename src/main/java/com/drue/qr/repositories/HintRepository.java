package com.drue.qr.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.drue.qr.models.Hint;

@Repository
public interface HintRepository extends CrudRepository<Hint, Long> {
	List<Hint> findAll();
}
