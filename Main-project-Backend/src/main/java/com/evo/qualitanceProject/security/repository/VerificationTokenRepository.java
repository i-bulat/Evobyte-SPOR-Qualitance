package com.evo.qualitanceProject.security.repository;

import com.evo.qualitanceProject.repository.DefaultRepository;
import com.evo.qualitanceProject.security.model.VerificationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends DefaultRepository<VerificationToken,Long> {
    VerificationToken findVerificationTokenByToken(String token);
}
