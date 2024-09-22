package cr.ac.una.springbootaopmaven.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class PersonaAspect {
    private static final Logger logger = Logger.getLogger(PersonaAspect.class.getName());

    @Before("execution(* cr.ac.una.springbootaopmaven.controller.PersonaController.savePersona*(..))")
    public void logBeforeV2(JoinPoint joinPoint) {
        logger.info("ENTPOINT TTERMINADO ... " + joinPoint.getSignature().getName());
    }

@Around("execution(* cr.ac.una.springbootaopmaven.controller.PersonaController.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long inicio = System.nanoTime();
    Object procedimiento = joinPoint.proceed();
    long tiempoEjecucion = System.nanoTime() - inicio;
    long tiempoEjecucionMilisegundos = tiempoEjecucion / 1000000;

    logger.info("La Funcion "+ joinPoint.getSignature().getName() + "Fue Ejecutada en " + tiempoEjecucionMilisegundos + " ms");

    return procedimiento;
    }
}
