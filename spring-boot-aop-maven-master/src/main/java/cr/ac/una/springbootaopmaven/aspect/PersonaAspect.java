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

    // Log antes de ejecutar el método guardarPersona
    @Before("execution(* cr.ac.una.springbootaopmaven.controller.PersonaController.guardarPersona*(..))")
    public void logAntesDeGuardar(JoinPoint joinPoint) {
        logger.info("Iniciando método: " + joinPoint.getSignature().getName() + " en PersonaController");
    }

    // Log alrededor de todos los métodos del controlador
    @Around("execution(* cr.ac.una.springbootaopmaven.controller.PersonaController.*(..))")
    public Object logAlrededor(ProceedingJoinPoint joinPoint) throws Throwable {
        long inicio = System.nanoTime();
        Object procedimiento = joinPoint.proceed();
        long tiempoEjecucion = System.nanoTime() - inicio;
        long tiempoEjecucionMilisegundos = tiempoEjecucion / 1000000;

        logger.info("El método " + joinPoint.getSignature().getName() + " fue ejecutado en " + tiempoEjecucionMilisegundos + " ms");
        return procedimiento;
    }

    // Log después de ejecutar métodos de generación de reportes
    @After("execution(* cr.ac.una.springbootaopmaven.controller.PersonaController.generarReporte*(..))")
    public void logDespuesDeGenerarReporte(JoinPoint joinPoint) {
        logger.info("Reporte generado por método: " + joinPoint.getSignature().getName() + " en PersonaController");
    }
}
