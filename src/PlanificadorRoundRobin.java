import java.util.*;

public class PlanificadorRoundRobin extends Planificador {
    private int quantum;

    public PlanificadorRoundRobin(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }

    @Override
    public List<String> ejecutar() { // ejecuta el planificador Round Robin
        Queue<Proceso> cola = new LinkedList<>();
        List<Proceso> auxiliar = new ArrayList<>(procesos);
        auxiliar.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        List<String> mapeoFinal = new ArrayList<>();
        Map<String, Integer> rafagasRestantes = new HashMap<>();

        for (Proceso p : auxiliar) {
            rafagasRestantes.put(p.getNombre(), p.getDuracion());
        }

        int tiempoActual = 0;

        System.out.println("Planificación Round Robin de quantum " + quantum + ":");
        while (!cola.isEmpty() || !auxiliar.isEmpty()) {
            verificarLlegada(auxiliar, tiempoActual, cola);

            if (cola.isEmpty()) {
                mostrarEncabezadoTiempo(tiempoActual);
                mostrarCpuInactiva(tiempoActual);
                tiempoActual++;
                espera();
                continue;
            }

            Proceso p = cola.poll();
            int rafagasPendientes = rafagasRestantes.get(p.getNombre());
            int tiempoEjecutado = Math.min(quantum, rafagasPendientes);

            tiempoActual = ejecutarProceso(p, tiempoEjecutado, tiempoActual, rafagasPendientes, mapeoFinal, cola,
                    auxiliar);

            int nuevasRafagas = rafagasPendientes - tiempoEjecutado;
            if (nuevasRafagas > 0) {
                rafagasRestantes.put(p.getNombre(), nuevasRafagas);
                cola.add(p);
            }

        }
        impresionFinal(tiempoActual);
        return mapeoFinal;
    }

    private void verificarLlegada(List<Proceso> auxiliar, int tiempoActual, Queue<Proceso> cola) { // verifica en qué
                                                                                                   // tiempo llega cada
                                                                                                   // proceso
        while (!auxiliar.isEmpty() && auxiliar.get(0).getTiempoDeLlegada() <= tiempoActual) {
            Proceso actual = auxiliar.remove(0);
            cola.add(actual);
            mostrarEncabezadoTiempo(tiempoActual);
            mostrarLlegadaProceso(actual);
            espera();
        }
    }

    private int ejecutarProceso(Proceso actual, int tiempoEjecutado, int tiempoActual, int rafagasPendientes,
            Collection mapeoFinal, Queue<Proceso> cola, List<Proceso> auxiliar) // ejecuta un proceso especificado
    {
        for (int i = 0; i < tiempoEjecutado; i++) {
            verificarLlegada(auxiliar, tiempoActual, cola);
            actual.setTiempoPrimeraEjecucion(tiempoActual);
            mostrarEncabezadoTiempo(tiempoActual);
            System.out.println("Ejecutando " + actual.getNombre());
            System.out.println("Ráfagas restantes: " + (rafagasPendientes - i - 1));
            mapeoFinal.add(actual.getNombre());
            tiempoActual++;
            imprimirColaDeListos(cola);
        }
        actual.ejecutar(tiempoEjecutado);
        revisionProceso(actual, tiempoActual);
        espera();
        return tiempoActual;
    }
}
