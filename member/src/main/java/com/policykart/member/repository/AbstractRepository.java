package com.policykart.member.repository;

import com.policykart.member.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity, I> extends JpaRepository<T, I> {

}