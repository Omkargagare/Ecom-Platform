package com.omkar.ecom.repository;

import com.omkar.ecom.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistTokenRepo extends JpaRepository<BlacklistToken, String> {
}
