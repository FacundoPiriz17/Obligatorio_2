import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlanificadorRoundRobin extends Planificador {
    private int quantum;

    public PlanificadorRoundRobin(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }
    @Override
    public void ejecutar() {
        Queue<Proceso> cola = new LinkedList<>();
        procesos.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        int tiempoActual = 0;
        int indice = 0;
        System.out.println("Planificación Round Robin de quantum " + quantum +":");
        while (!cola.isEmpty() || indice < procesos.size()) {
            while (indice < procesos.size() && procesos.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                cola.add(procesos.get(indice++));
            }

            if (cola.isEmpty()) {
                tiempoActual++;
                continue;
            }

            Proceso p = cola.poll();
            int tiempoEjecutado = Math.min(quantum, p.getTiempoRestante());

            for (int i = 0; i < tiempoEjecutado; i++) {
                System.out.println("Tiempo " + tiempoActual + ": ejecutando " + p.getNombre());
                p.ejecutar(1);
                tiempoActual++;

                while (indice < procesos.size() && procesos.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                    cola.add(procesos.get(indice++));
                }
            }

            if (!p.TodaviaEnEjecucion()){
                cola.add(p);
            }
        }
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
    }
}
