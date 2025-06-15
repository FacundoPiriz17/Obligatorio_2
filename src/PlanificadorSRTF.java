import java.util.*;

public class PlanificadorSRTF extends Planificador {

    public PlanificadorSRTF(List<Proceso> procesos) {
        super(procesos);
    }

    @Override
    public List<String> ejecutar() {
        List<Proceso> lista = new ArrayList<>(procesos);
        lista.sort(Comparator.comparingInt(Proceso::getTiempoDeLlegada));
        PriorityQueue<Proceso> cola = new PriorityQueue<>(Comparator.comparingInt(Proceso::getTiempoRestante));
        List<String> mapeoFinal = new ArrayList<>();
        int tiempoActual = 0;
        int indice = 0;

        System.out.println("Planificación SRTF: ");

        while (!cola.isEmpty() || indice < lista.size()) {

            indice = verificarLlegadas(lista,cola,indice,tiempoActual);
            espera();

            if (cola.isEmpty()) {
                mostrarEncabezadoTiempo(tiempoActual);
                mostrarCpuInactiva(tiempoActual);
                tiempoActual++;
                espera();
                continue;
            }

            Proceso actual = cola.poll();
            actual.setTiempoPrimeraEjecucion(tiempoActual);
            tiempoActual = ejecutarProceso(actual,tiempoActual,mapeoFinal);

            imprimirColaDeListos(cola);
            indice = verificarLlegadas(lista,cola,indice,tiempoActual);
            espera();


            revisionProceso(actual,tiempoActual);

            if (!actual.ejecucionFinalizada()) {
                cola.add(actual);
            }
        }
        impresionFinal(tiempoActual);
        return mapeoFinal;
    }

    private int verificarLlegadas(List<Proceso> lista, PriorityQueue<Proceso> cola, int indice, int tiempoActual) {
        while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
            Proceso nuevo = lista.get(indice++);
            cola.add(nuevo);
            mostrarEncabezadoTiempo(tiempoActual);
            mostrarLlegadaProceso(nuevo);
        }
        return indice;
    }

    private int ejecutarProceso(Proceso actual, int tiempoActual, Collection mapeoFinal) {
        mostrarEncabezadoTiempo(tiempoActual);
        System.out.println("Ejecutando " + actual.getNombre());
        System.out.println("Ráfagas restantes: " + (actual.getTiempoRestante() - 1));
        mapeoFinal.add(actual.getNombre());
        actual.ejecutar(1);
        tiempoActual++;
        return  tiempoActual;
    }

}
