package com.policykart.claim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@MappedSuperclass
public class AbstractEntity implements Serializable {
    @Id
    @Column(
            name = "id",
            nullable = false,
            unique = true,
            columnDefinition = "char(40) not null")
    private String id;

    @Column(name = "created_at", nullable = false, columnDefinition = "bigint not null")
    private Long createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "bigint not null")
    private Long updatedAt;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.id = id == null ? UUID.randomUUID().toString().replace("-", "") : id;
        preUpdate();
        this.setCreatedAt(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(System.currentTimeMillis());
    }
}
