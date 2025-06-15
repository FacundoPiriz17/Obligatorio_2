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
            while (indice < listaEspera.size() && listaEspera.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = listaEspera.get(indice++);
                cola.add(nuevo);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + nuevo.getNombre());
                System.out.println("Ráfaga de " + nuevo.getDuracion());
                System.out.println("Prioridad: " + nuevo.getPrioridad());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
            }

            if (actual == null && cola.isEmpty()) {
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
                tiempoActual++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
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
            System.out.println("-----------------------------------");
            System.out.println("Tiempo " + tiempoActual + ":");
            System.out.println("Ejecutando " + actual.getNombre());
            System.out.println("Ráfagas restantes: " + (actual.getTiempoRestante() - 1));
            mapeoFinal.add(actual.getNombre());
            actual.ejecutar(1);
            tiempoActual++;

            while (indice < listaEspera.size() && listaEspera.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = listaEspera.get(indice++);
                cola.add(nuevo);
                System.out.println("Llega " + nuevo.getNombre());
                System.out.println("Ráfaga de " + nuevo.getDuracion());
                System.out.println("Prioridad: " + nuevo.getPrioridad());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
            }

            System.out.println("Cola de listos: " + cola.stream().sorted(Comparator.comparingInt(Proceso::getPrioridad)).map(Proceso::getNombre).toList());

            if (actual.ejecucionFinalizada()) {
                actual.setTiempoDeFinalizacion(tiempoActual);
                actual.setTiempoDeRetorno();
                actual = null;
            }

            for (Proceso proceso : cola) {
                proceso.aumentarTiempoDeEspera();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupción inesperada");
                return null;
            }


        }

        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
        return mapeoFinal;
    }
}
