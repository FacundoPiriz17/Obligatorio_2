import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PlanificadorSRTF extends Planificador {

    public PlanificadorSRTF(List<Proceso> procesos) {
        super(procesos);
    }

    @Override
    public void ejecutar() {
        List<Proceso> lista = new ArrayList<>(procesos);
        lista.sort(Comparator.comparingInt(Proceso::getTiempoDeLlegada));
        PriorityQueue<Proceso> cola = new PriorityQueue<>(Comparator.comparingInt(Proceso::getTiempoRestante));
        int tiempoActual = 0;
        int indice = 0;
        System.out.println("Planificación SRTF: ");
        while (!cola.isEmpty() || indice < lista.size()) {
            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                cola.add(lista.get(indice++));
            }

            if (cola.isEmpty()) {
                tiempoActual++;
                continue;
            }

            Proceso actual = cola.poll();
            System.out.println("Tiempo " + tiempoActual + ": ejecutando " + actual.getNombre());
            actual.ejecutar(1);
            tiempoActual++;

            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                cola.add(lista.get(indice++));
            }

            if (!actual.TodaviaEnEjecucion()) {
                cola.add(actual);
            }
        }

    }

}
