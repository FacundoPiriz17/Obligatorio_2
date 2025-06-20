import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class Planificador {
    protected List<Proceso> procesos = new LinkedList<Proceso>();

    public Planificador(List<Proceso> procesos) {
        for (Proceso p : procesos) {
            this.procesos.add(p.clonar());
        }
    }

    public abstract List<String> ejecutar();

    protected void espera() { // proboca una demora de 0.5 segundos
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupción inesperada");
        }
    }

    protected void mostrarCpuInactiva(int tiempoActual) { // Muestra cuando la CPU está inactiva
        System.out.println("-----------------------------------");
        System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
    }

    protected void mostrarLlegadaProceso(Proceso p) { // Muestra cuando un proceso llega a la CPU
        System.out.println("Llega " + p.getNombre());
        System.out.println("Ráfaga de " + p.getDuracion());
        if (p.getPrioridad() != 0) {
            System.out.println("Prioridad: " + p.getPrioridad());
        }
    }

    protected void mostrarEncabezadoTiempo(int tiempoActual) {
        System.out.println("-----------------------------------");
        System.out.println("Tiempo " + tiempoActual + ":");
    }

    protected void revisionProceso(Proceso actual, int tiempoActual) { // Revisa el estado de un proceso
        if (actual.ejecucionFinalizada()) {
            actual.setTiempoDeFinalizacion(tiempoActual);
            actual.setTiempoDeRetorno();
            actual.setTiempoDeEspera();
        }
    }

    protected void impresionFinal(int tiempoActual) {
        System.out.println("---------------------------------");
        System.out.println("\nLa ejecución duró un total de " + tiempoActual + " ráfagas");
    }

    protected void imprimirColaDeListos(Collection<Proceso> listaEspera) {
        System.out.println("Cola de listos: " + listaEspera.stream().map(Proceso::getNombre).toList());
    }

    public void mostrarMatriz() { // muestra matriz con tiempo de espera, tiempo de retorno, tiempo de respuesta
        mostrarMatriz(procesos);
    }

    public void mostrarMatriz(List<Proceso> lista) { // crea el formato para mostrar la matriz
        int[][] matriz = new int[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            Proceso proceso = lista.get(i);
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
        for (int i = 0; i < lista.size(); i++) {
            Proceso proceso = lista.get(i);
            System.out.printf(formatoEncabezado, proceso.getNombre(),
                    matriz[i][0], matriz[i][1], matriz[i][2]);
        }
        System.out.println(lineaSeparadora);
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

}