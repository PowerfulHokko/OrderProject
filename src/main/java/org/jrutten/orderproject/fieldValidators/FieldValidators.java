package org.jrutten.orderproject.fieldValidators;

import org.apache.commons.validator.EmailValidator;

import java.util.Arrays;
import java.util.Objects;

public class FieldValidators {
    public static void guardStringNullAndBlank(String... inputs) throws IllegalArgumentException{
        if (Arrays.stream(inputs).anyMatch(in -> in == null || in.isBlank())) throw new IllegalArgumentException("Input field cannot be blank");
    }

    public static void guardObjectNull(Object... inputs) throws IllegalArgumentException{
        if (Arrays.stream(inputs).anyMatch(Objects::isNull)) throw new IllegalArgumentException("Input field cannot be null");
    }

    public static void guardZeroOrLessThan(int... inputs){
        if(Arrays.stream(inputs).anyMatch(in -> in<=0)) throw new IllegalArgumentException("Input cannot be less than or equal to zero");
    }

    public static void guardLessThanZero(int... inputs){
        if(Arrays.stream(inputs).anyMatch(in -> in<0)) throw new IllegalArgumentException("Input cannot be less than zero");
    }

    public static void validateEmail(String email){
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
