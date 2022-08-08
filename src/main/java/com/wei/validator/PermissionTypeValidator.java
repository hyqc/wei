package com.wei.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PermissionTypeValidatorClass.class)
public @interface PermissionTypeValidator {

    String message() default "权限类型格式错误";

    String value() default "";

    boolean includeAll() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
