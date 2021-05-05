package me.widua.databaseauthorization.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = MaxListSizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxListSize {
    String message() default "Invalid List size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
