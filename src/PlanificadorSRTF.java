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

            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = lista.get(indice++);
                cola.add(nuevo);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + nuevo.getNombre());
                System.out.println("Ráfaga de " + nuevo.getDuracion());
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
            }


            if (cola.isEmpty()) {
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
                tiempoActual++;
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
                continue;
            }

            Proceso actual = cola.poll();
            actual.setTiempoPrimeraEjecucion(tiempoActual);
            System.out.println("-----------------------------------");
            System.out.println("Tiempo " + tiempoActual+":");
            System.out.println("Ejecutando " + actual.getNombre());
            System.out.println("Ráfagas restantes: " + (actual.getTiempoRestante() - 1));
            mapeoFinal.add(actual.getNombre());
            actual.ejecutar(1);
            tiempoActual++;

            while (indice < lista.size() && lista.get(indice).getTiempoDeLlegada() <= tiempoActual) {
                Proceso nuevo = lista.get(indice++);
                cola.add(nuevo);
                System.out.println("Llega " + nuevo.getNombre());
                System.out.println("Ráfaga de " + nuevo.getDuracion());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
            }

            List<String> nombres = cola.stream().sorted(Comparator.comparingInt(Proceso::getTiempoRestante)).map(Proceso::getNombre).toList();
            System.out.println("Cola de listos: " + nombres);

            if (actual.ejecucionFinalizada()) {
                actual.setTiempoDeFinalizacion(tiempoActual);
                actual.setTiempoDeRetorno();
                actual.setTiempoDeEsperaExpropiativo();
            } else {
                cola.add(actual);
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
