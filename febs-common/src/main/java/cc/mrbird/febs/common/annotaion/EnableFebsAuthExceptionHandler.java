package cc.mrbird.febs.common.annotaion;

import cc.mrbird.febs.common.configure.FebsAuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(FebsAuthExceptionConfigure.class)
public @interface EnableFebsAuthExceptionHandler {
}
