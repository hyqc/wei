package com.wei.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorClass implements ConstraintValidator<DateValidator, String> {

    private DateTimeFormatter ldt;

    @Override
    public void initialize(DateValidator validator) {
        this.ldt = DateTimeFormatter.ofPattern(validator.format());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = true;
        if (s==null) {
            return true;
        }
        try {
            LocalDate.parse(s, ldt);
        } catch (DateTimeParseException| NullPointerException e) {
            flag = false;
        }
        return flag;
    }
}
