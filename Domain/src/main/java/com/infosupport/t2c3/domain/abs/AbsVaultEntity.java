package com.infosupport.t2c3.domain.abs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import lombok.Getter;

/**
 * Created by Stoux on 18/01/2016.
 */
@MappedSuperclass
public abstract class AbsVaultEntity extends AbsEntity implements DataVaultEnabled {

    /* Most useless constant ever */
    private static final int THREE_DIGITS = 3;

    @JsonIgnore
    @Getter
    private String businessKey;

    /**
     * Fill the business key field if it's not set yet.
     */
    @SuppressWarnings("squid:UnusedPrivateMethod")
    @PostPersist
    private void fillBusinessKey() {
        if (businessKey == null) {
            businessKey = generateBusinessKey();
        }
    }

    /**
     * Generate a Business key for this object.
     * This key should be unique, application wide.
     *
     * @return the key
     */
    protected abstract String generateBusinessKey();

    /**
     * Get only the first three digits of the given string.
     * This takes less than 3 if it's shorter. It also calls #toUpperCase()
     * on the result.
     *
     * @param thing The string
     * @return The resulting string
     */
    protected String asThreeDigits(String thing) {
        int thingLength = thing.length();
        String result = thing.substring(0, thingLength < THREE_DIGITS ? thingLength : THREE_DIGITS);
        return result.toUpperCase();
    }

}
