import java.util.ArrayList;
import java.util.List;

public class PlanificadorSJF extends Planificador {
    public PlanificadorSJF(List<Proceso> procesos) {
        super(procesos);
    }
    @Override
    public void ejecutar() {
        List<Proceso> lista = new ArrayList<>(procesos);
        lista.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        int tiempoActual = 0;
        List<Proceso> listaEspera = new ArrayList<>();
        List<String> mapeoFinal = new ArrayList<>();

        System.out.println("Planificador SJF:");
        while (!listaEspera.isEmpty() || !lista.isEmpty()) {
            while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
                Proceso actual = lista.remove(0);
                listaEspera.add(actual);
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ":");
                System.out.println("Llega " + actual.getNombre() + " con ráfaga de " + actual.getDuracion());
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return;
                }
            }

            if (listaEspera.isEmpty()) {
                System.out.println("-----------------------------------");
                System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
                tiempoActual++;
                continue;
            }

            listaEspera.sort((p1,p2) -> Integer.compare(p1.getDuracion(), p2.getDuracion()));
            Proceso p = listaEspera.remove(0);

            for (int i = 0; i < p.getDuracion(); i++) {
                System.out.println("-----------------------------------");
                System.out.println("Ejecutando " + p.getNombre() + ", ráfagas restantes: " + (p.getDuracion() - i - 1));

                mapeoFinal.add(p.getNombre());
                tiempoActual++;

                while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
                    Proceso nuevo = lista.remove(0);
                    listaEspera.add(nuevo);

                    System.out.println("Llega " + nuevo.getNombre() + " con ráfaga de " + nuevo.getDuracion());
                }

                System.out.println("Cola de listos: " + listaEspera.stream().map(Proceso::getNombre).toList());

                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupción inesperada");
                    return;
                }
            }
        }
        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("Lista de ejecución final:");
        System.out.println(mapeoFinal);
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
    }
}
