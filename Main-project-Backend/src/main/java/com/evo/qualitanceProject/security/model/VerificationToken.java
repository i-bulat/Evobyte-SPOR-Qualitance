package com.evo.qualitanceProject.security.model;


import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VerificationToken extends BaseEntity<Long> {
    private static final int EXPIRATION_HOURS = 24;

    private String token;

    @OneToOne
    @JoinColumn(nullable = false)
    private AppUser user;

    private LocalDateTime expiryDate;

    public VerificationToken(final String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION_HOURS);
    }

    public VerificationToken(final String token, final AppUser user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION_HOURS);
    }

    private LocalDateTime calculateExpiryDate(final int expiryTimeInHours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusHours(expiryTimeInHours);
        return expiration;
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION_HOURS);
    }
}
