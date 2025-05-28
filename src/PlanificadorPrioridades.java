import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PlanificadorPrioridades extends Planificador {

    private boolean expropiativo;

    public PlanificadorPrioridades(List<Proceso> procesos, boolean expropiativo) {
        super(procesos);
        this.expropiativo = expropiativo;
    }

    @Override
    public void ejecutar() {
        List <Proceso> listaEspera = new ArrayList<>(procesos);
        listaEspera.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        PriorityQueue<Proceso> cola = new PriorityQueue<>(Comparator.comparing(Proceso::getPrioridad));
        int tiempoActual = 0;
        int indice = 0;

        System.out.println("Planificador Por prioridades " + (expropiativo ? "expropiativo" : "no expropiativo") +":");

        Proceso actual = null;

        while (!cola.isEmpty() || indice < listaEspera.size() || actual != null) {
            while (indice < listaEspera.size() && listaEspera.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                cola.add(listaEspera.get(indice++));
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
            System.out.println("Tiempo " + tiempoActual + ": ejecutando " + actual.getNombre());
            actual.ejecutar(1);
            tiempoActual++;

            if (actual.TodaviaEnEjecucion()) {
                actual = null;
            }
        }
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
    }
}
