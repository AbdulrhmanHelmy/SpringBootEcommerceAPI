package com.helmy.ecommerce.AOP;

import com.helmy.ecommerce.Response.API_Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {

    @Around("execution(* com.helmy.ecommerce.Controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        System.out.println("Invoking method: " + methodName);
        System.out.println("Parameters: " + Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();

            System.out.println("Method executed successfully: " + methodName);
            System.out.println("Returned value: " + result);

            return result;
        } catch (Throwable ex) {
            System.err.println("Exception in method: " + methodName);
            System.err.println("Exception message: " + ex.getMessage());
            API_Response apiResponse = new API_Response();
            apiResponse.setMessage("Error: " + ex.getMessage());
            apiResponse.setData(null);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
        }
}
