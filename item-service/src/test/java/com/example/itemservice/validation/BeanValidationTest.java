package com.example.itemservice.validation;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.itemservice.item.domain.item.Item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


public class BeanValidationTest {

    @Test
    void beanValidation() {
        // 검증기 생성
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItemName("   "); // 공백
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for(ConstraintViolation<Item> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }

}
