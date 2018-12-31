package nju.hwx;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface TestAnnotation {
    String Author() default "161190037";
}