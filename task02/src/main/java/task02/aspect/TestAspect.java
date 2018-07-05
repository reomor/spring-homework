package task02.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class TestAspect {

    @Pointcut("@annotation(task02.annotation.LogFunctionParameters)")
    public void annotationLogFunctionParameters() {
    }

    // doesn't work
    // @Around("@annotation(task02.annotation.LogFunctionParameters) && execution(* task02..*.*(..))")
    //@Around("execution(@task02.annotation.LogFunctionParameters * task02..*.*(..))")
    //@Around("execution(@logFunctionParameters * task02..*.*(..))")
    //@Around("execution(* task02..*.*(..)) && @annotation(task02.annotation.LogFunctionParameters)")
    @Around("execution(* task02..*.*(..))")
    //public Object logFunctionParameters(ProceedingJoinPoint proceedingJoinPoint, LogFunctionParameters logFunctionParameters) throws Throwable {
    //@Around("@annotation(task02.annotation.LogFunctionParameters)")
    public Object logFunctionParameters(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long executonTimeInMills = System.currentTimeMillis() - start;
        System.out.println(proceedingJoinPoint.getSignature().getName() + "(" + Arrays.toString(proceedingJoinPoint.getArgs()) + ") executed in " + executonTimeInMills + "ms");
        return proceed;
    }
}
