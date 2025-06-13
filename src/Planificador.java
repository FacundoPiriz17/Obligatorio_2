import java.util.List;

public abstract class Planificador {
    protected List<Proceso> procesos;

    public Planificador(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public abstract List<String> ejecutar();

    public void mostrarMatriz() {
        int[][] matriz = new int[procesos.size()][3];

        for (int i = 0; i < procesos.size(); i++) {
            Proceso proceso = procesos.get(i);
            matriz[i][0] = proceso.getTiempoDeEspera();
            matriz[i][1] = proceso.getTiempoDeRetorno();
            matriz[i][2] = proceso.getTiempoDeRespuesta();
        }

        // Encabezado
        String formatoEncabezado = "| %-10s | %-17s | %-17s | %-19s |%n";
        String lineaSeparadora = "+------------+-------------------+-------------------+---------------------+";

        System.out.println("Matriz de tiempos:");
        System.out.println(lineaSeparadora);
        System.out.printf(formatoEncabezado, "Proceso", "Tiempo de Espera", "Tiempo de Retorno", "Tiempo de Respuesta");
        System.out.println(lineaSeparadora);

        // Filas
        for (int i = 0; i < procesos.size(); i++) {
            Proceso proceso = procesos.get(i);
            System.out.printf(formatoEncabezado, proceso.getNombre(),
                    matriz[i][0], matriz[i][1], matriz[i][2]);
        }
        System.out.println(lineaSeparadora);
    }

}