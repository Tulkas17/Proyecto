package cr.ac.una.springbootaopmaven.entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAJson {

    public static void main(String[] args) throws IOException {
        // Obtener la ruta dinámica al archivo log desde la carpeta raíz del proyecto
        Path rutaArchivoLog = Paths.get(System.getProperty("user.dir"), "app.log");

        // Leer y procesar el archivo de log
        try (Stream<String> flujoLog = Files.lines(rutaArchivoLog)) {

            // Mapear cada línea a un objeto EntradaLog y recolectar como una lista
            var entradasLog = flujoLog
                    .map(LogAJson::parsearLineaLog) // Transforma cada línea en un EntradaLog
                    .collect(Collectors.toList());

            // Convertir la lista de objetos EntradaLog a JSON
            ObjectWriter escritorJson = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String resultadoJson = escritorJson.writeValueAsString(entradasLog);

            // Imprimir o guardar el resultado JSON
            System.out.println(resultadoJson);
        }
    }

    // Método para parsear una línea del log en un objeto EntradaLog
    private static EntradaLog parsearLineaLog(String lineaLog) {
        // Personaliza según el formato del log
        String[] partes = lineaLog.split(" ");
        String marcaDeTiempo = partes[0];  // Ajusta según el formato de tu log
        String nivel = partes[1];
        String mensaje = partes[2];

        return new EntradaLog(marcaDeTiempo, nivel, mensaje); // Ajusta según tus datos
    }

    // Clase EntradaLog para guardar los detalles del log
    static class EntradaLog {
        private String marcaDeTiempo;
        private String nivel;
        private String mensaje;

        public EntradaLog(String marcaDeTiempo, String nivel, String mensaje) {
            this.marcaDeTiempo = marcaDeTiempo;
            this.nivel = nivel;
            this.mensaje = mensaje;
        }

        // Getters y setters...
    }
}
