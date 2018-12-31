package nju.hwx;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface MyAnnotation {
    String Author() default "161190037";
}