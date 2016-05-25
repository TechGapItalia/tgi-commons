package it.techgap.common.security.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface AopSecured {

}
