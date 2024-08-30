package com.policykart.auth.repository;

import com.policykart.auth.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity, I> extends JpaRepository<T, I> {

}