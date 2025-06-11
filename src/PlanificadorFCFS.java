import java.util.ArrayList;
import java.util.List;

public class PlanificadorFCFS extends Planificador {
    public PlanificadorFCFS(List<Proceso> procesos) {
        super(procesos);
    }

    @Override
    public List<String> ejecutar() {
        List<Proceso> lista = new ArrayList<>(procesos);
        lista.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        int tiempoActual = 0;
        List<Proceso> listaEspera = new ArrayList<>();
        List<String> mapeoFinal = new ArrayList<>();

        System.out.println("Planificación FCFS:");
        while (!listaEspera.isEmpty() || !lista.isEmpty()) {
            while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
                Proceso actual = lista.remove(0);
                listaEspera.add(actual);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual+":");
                System.out.println("Llega " + actual.getNombre());
                System.out.println("Ráfaga de " + actual.getDuracion());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }
            }

            if (listaEspera.isEmpty()) {
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


            Proceso p = listaEspera.remove(0);

            for (int i = 0; i < p.getDuracion(); i++) {
                System.out.println("---------------------------------");
                System.out.println("Tiempo " + tiempoActual+":");
                System.out.println("Ejecutando " + p.getNombre());
                System.out.println("Ráfagas restantes: " + (p.getDuracion() - i - 1));
                mapeoFinal.add(p.getNombre());
                tiempoActual++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return null;
                }

                while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
                    Proceso nuevo = lista.remove(0);
                    listaEspera.add(nuevo);
                    System.out.println("Llega " + nuevo.getNombre());
                    System.out.println("Ráfaga de " + nuevo.getDuracion());
                }
                System.out.println("Cola de listos: " + listaEspera.stream().map(Proceso::getNombre).toList());
            }
        }
        System.out.println("---------------------------------");
        System.out.println();
        System.out.println("Lista de ejecución final:");
        System.out.println(mapeoFinal);
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
        return mapeoFinal;
    }
}
