package com.traumaticevolutions.tevosales_backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Valida un número de tarjeta mediante el algoritmo de Luhn.
 * 
 * @author Ángel Aragón
 */
public class LuhnValidator implements ConstraintValidator<ValidCardNumber, String> {

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        if (cardNumber == null || !cardNumber.matches("\\d+")) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9)
                    n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
