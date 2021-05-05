package me.widua.databaseauthorization.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxListSizeValidator implements ConstraintValidator<MaxListSize, List<?>> {
    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        if (list == null){
            return false;
        }else {
            return list.size() == 3;
        }
    }
}
