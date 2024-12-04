package com.upiiz.securitydb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.securitydb.entities.RoleEntity;

public interface RolRepository extends JpaRepository<RoleEntity, Long>{

}
