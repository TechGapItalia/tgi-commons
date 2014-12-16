package it.tgi.common.validation;

import it.tgi.common.validation.validator.ReferencesValidator;
import org.springframework.data.domain.Persistable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ReferencesValidator.class)
@Documented
public @interface References {

    String message() default "{it.tgi.common.validation.references}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    Class<? extends Persistable> value();

}