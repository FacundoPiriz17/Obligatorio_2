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

        System.out.println("Planificador SJF:");

        System.out.println();

        while (!listaEspera.isEmpty() || !lista.isEmpty()) {
            while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
                listaEspera.add(lista.remove(0));
            }
            if (listaEspera.isEmpty()) {
                tiempoActual++;
                continue;
            }

            listaEspera.sort((p1,p2) -> Integer.compare(p1.getDuracion(), p2.getDuracion()));
            Proceso p = listaEspera.remove(0);
            for (int i = 0; i < p.getDuracion(); i++) {
                System.out.println("Tiempo " + tiempoActual + ": ejecutando " + p.getNombre());
                tiempoActual++;
            }
        }
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
    }
}
