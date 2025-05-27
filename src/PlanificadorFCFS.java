import java.util.List;

public class PlanificadorFCFS extends Planificador {
    public PlanificadorFCFS(List<Proceso> procesos) {
        super(procesos);
    }

    @Override
    public void ejecutar() {
        procesos.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        int tiempoActual = 0;
        System.out.println("Planificación FCFS:");
        for (Proceso p : procesos) {
            while (tiempoActual < p.getTiempoDeLlegada()) {
                System.out.println("Tiempo " + tiempoActual + ": CPU inactiva");
                tiempoActual++;
            }

            for (int i = 0; i < p.getDuracion(); i++) {
                System.out.println("Tiempo " + tiempoActual + ": ejecutando " + p.getNombre());
                tiempoActual++;
            }
        }
        System.out.println("La ejecución duró un total de " + tiempoActual + " ráfagas");
    }
}
