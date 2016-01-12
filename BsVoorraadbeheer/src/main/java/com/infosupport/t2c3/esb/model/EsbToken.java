package com.infosupport.t2c3.esb.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Stoux on 12/01/2016.
 */
@Getter
@AllArgsConstructor
public class EsbToken {

    /** The time a token is valid. */
    private static final long TOKEN_LIFESPAN = 2 * 60 * 60 * 1000;

    private String token;
    @Setter
    private EsbTask task;

    private long expiresAt;

    /**
     * Generate a random token.
     *
     * @return The token
     */
    public static EsbToken generateToken(EsbTask task) {
        return new EsbToken(
                UUID.randomUUID().toString(),
                task,
                System.currentTimeMillis() + TOKEN_LIFESPAN
        );
    }

}
