package com.omkar.ecom.repository;

import com.omkar.ecom.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
}
