import java.util.*;

public class PlanificadorRoundRobin extends Planificador {
    private int quantum;

    public PlanificadorRoundRobin(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }
    @Override
    public void ejecutar() {
        Queue<Proceso> cola = new LinkedList<>();
        List<Proceso> auxiliar = new ArrayList<>(procesos);
        auxiliar.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));

        List<String> mapeoFinal = new ArrayList<>();
        Map<String, Integer> rafagasRestantes = new HashMap<>();

        for (Proceso p : auxiliar) {
            rafagasRestantes.put(p.getNombre(), p.getDuracion());
        }

        int tiempoActual = 0;

        System.out.println("Planificación Round Robin de quantum " + quantum +":");
        while (!cola.isEmpty() || !auxiliar.isEmpty()) {
            while (!auxiliar.isEmpty() && auxiliar.get(0).getTiempoDeLlegada() <= tiempoActual) {
                Proceso actual = auxiliar.remove(0);
                cola.add(actual);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + actual.getNombre() + " con ráfaga de " + actual.getDuracion());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
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

            Proceso p = cola.poll();
            int rafagasPendientes = rafagasRestantes.get(p.getNombre());
            int tiempoEjecutado = Math.min(quantum, rafagasPendientes);

            for (int i = 0; i < tiempoEjecutado; i++) {
                System.out.println("-----------------------------------");
                System.out.println("Ejecutando " + p.getNombre() + ", ráfagas restantes: " + (rafagasPendientes - i - 1));
                mapeoFinal.add(p.getNombre());
                tiempoActual++;

                while (!auxiliar.isEmpty() && auxiliar.get(0).getTiempoDeLlegada() <= tiempoActual) {
                    Proceso nuevo = auxiliar.remove(0);
                    cola.add(nuevo);
                    System.out.println("Llega " + nuevo.getNombre() + " con ráfaga de " + nuevo.getDuracion());
                }
                List<String> nombres = cola.stream().map(Proceso::getNombre).toList();
                System.out.println("Cola de listos: " + nombres);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return;
                }

            }

            int nuevasRafagas = rafagasPendientes - tiempoEjecutado;
            if (nuevasRafagas > 0) {
                rafagasRestantes.put(p.getNombre(), nuevasRafagas);
                cola.add(p);
            }

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
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");    }
}
