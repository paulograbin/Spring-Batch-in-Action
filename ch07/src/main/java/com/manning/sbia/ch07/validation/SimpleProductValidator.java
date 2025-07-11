/**
 * Simple product validator to replace the deprecated Spring Modules Valang validator
 */
package com.manning.sbia.ch07.validation;

import com.manning.sbia.ch01.domain.Product;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

/**
 * Custom validator that replaces the deprecated Spring Modules Valang validator
 * for demonstrating validation functionality.
 */
public class SimpleProductValidator implements Validator<Product> {

    @Override
    public void validate(Product product) throws ValidationException {
        if (product.getPrice() != null && product.getPrice().doubleValue() < 0) {
            throw new ValidationException("Product price cannot be negative!");
        }
    }
}
