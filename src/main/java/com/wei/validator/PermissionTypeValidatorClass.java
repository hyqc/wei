package com.wei.validator;

import com.wei.admin.pe.AdminPermissionTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Administrator
 */
public class PermissionTypeValidatorClass implements ConstraintValidator<PermissionTypeValidator, String> {
    private Boolean includeALl;

    @Override
    public void initialize(PermissionTypeValidator constraintAnnotation) {
        this.includeALl = constraintAnnotation.includeAll();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> types = Arrays.stream(AdminPermissionTypeEnum.values()).map(AdminPermissionTypeEnum::getValue).collect(Collectors.toSet());
        if (includeALl) {
            types.add("");
        }
        return types.contains(s);
    }
}
