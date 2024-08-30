package com.policykart.claim.repository;

import com.policykart.claim.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity, I> extends JpaRepository<T, I> {

}