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
        List<String> mapeoFinal = new ArrayList<>();
        int tiempoActual = 0;
        int indice = 0;

        System.out.println("Planificación SRTF: ");

        while (!cola.isEmpty() || indice < lista.size()) {
            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = lista.get(indice++);
                cola.add(nuevo);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + nuevo.getNombre() + " con ráfaga de " + nuevo.getDuracion());
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return;
                }
            }


            if (cola.isEmpty()) {
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
                tiempoActual++;
                continue;
            }

            Proceso actual = cola.poll();
            System.out.println("-----------------------------------");
            System.out.println("Ejecutando " + actual.getNombre() + ", ráfagas restantes: " + (actual.getTiempoRestante() - 1));
            mapeoFinal.add(actual.getNombre());
            actual.ejecutar(1);
            tiempoActual++;

            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = lista.get(indice++);
                cola.add(nuevo);
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + nuevo.getNombre() + " con ráfaga de " + nuevo.getDuracion());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return;
                }
            }

            if (!actual.TodaviaEnEjecucion()) {
                cola.add(actual);
            }

            List<String> nombres = cola.stream().sorted(Comparator.comparingInt(Proceso::getTiempoRestante)).map(Proceso::getNombre).toList();;
            System.out.println("Cola de listos: " + nombres);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupción inesperada");
                return;
            }

        }
        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("Lista de ejecución final:");
        System.out.println(mapeoFinal);
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");

    }

}
