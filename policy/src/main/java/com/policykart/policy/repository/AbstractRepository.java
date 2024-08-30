package com.policykart.policy.repository;

import com.policykart.policy.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity, I> extends JpaRepository<T, I> {

}
