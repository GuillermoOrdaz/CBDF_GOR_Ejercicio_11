package com.upiiz.securitydb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.securitydb.entities.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>{

}
