import java.util.*;

public class PlanificadorPrioridades extends Planificador {

    private boolean expropiativo;

    public PlanificadorPrioridades(List<Proceso> procesos, boolean expropiativo) {
        super(procesos);
        this.expropiativo = expropiativo;
    }

    @Override
    public List<String> ejecutar() {
        List <Proceso> listaEspera = new ArrayList<>(procesos);
        listaEspera.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        PriorityQueue<Proceso> cola = new PriorityQueue<>(Comparator.comparing(Proceso::getPrioridad));
        List<String> mapeoFinal = new ArrayList<>();
        int tiempoActual = 0;
        int indice = 0;

        System.out.println("Planificador Por prioridades " + (expropiativo ? "expropiativo" : "no expropiativo") +":");
        Proceso actual = null;
        while (!cola.isEmpty() || indice < listaEspera.size() || actual != null) {
            indice = verificarLlegada(listaEspera, cola, indice, tiempoActual);

            if (actual == null && cola.isEmpty()) {
                mostrarCpuInactiva(tiempoActual);
                tiempoActual++;
                espera();
                continue;
            }

            if (actual == null) {
                actual = cola.poll();
            }

            if (actual == null){
                tiempoActual++;
                continue;
            }

            if (expropiativo) {
                if (!cola.isEmpty() && cola.peek().getPrioridad() < actual.getPrioridad()) {
                    cola.add(actual);
                    actual = cola.poll();
                }
            }
            actual.setTiempoPrimeraEjecucion(tiempoActual);

            tiempoActual = ejecutarProceso(actual,tiempoActual,mapeoFinal);

            indice = verificarLlegada(listaEspera, cola, indice, tiempoActual);

            imprimirColaDeListos(cola);

            revisionProceso(actual, tiempoActual);
            if (actual.ejecucionFinalizada()) {
                actual = null;
            }
            espera();
        }

        impresionFinal(tiempoActual);
        return mapeoFinal;
    }

    public int verificarLlegada( List <Proceso> listaEspera, PriorityQueue<Proceso> cola, int indice ,int tiempoActual) {
        while (indice < listaEspera.size() && listaEspera.get(indice).getTiempoDeLlegada() <= tiempoActual) {
            Proceso nuevo = listaEspera.get(indice++);
            cola.add(nuevo);
            mostrarEncabezadoTiempo(tiempoActual);
            mostrarLlegadaProceso(nuevo);
            espera();
        }
        return indice;
    }

    public int ejecutarProceso(Proceso actual, int tiempoActual, Collection mapeoFinal) {
        mostrarEncabezadoTiempo(tiempoActual);
        System.out.println("Ejecutando " + actual.getNombre());
        System.out.println("Ráfagas restantes: " + (actual.getTiempoRestante() - 1));
        mapeoFinal.add(actual.getNombre());
        actual.ejecutar(1);
        tiempoActual++;
        return tiempoActual;
    }
}
