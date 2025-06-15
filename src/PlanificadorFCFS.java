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
            verificarLlegadas(lista, listaEspera, tiempoActual);

            if (listaEspera.isEmpty()) {
                mostrarEncabezadoTiempo(tiempoActual);
                mostrarCpuInactiva(tiempoActual);
                tiempoActual++;
                espera();
                continue;
            }

            Proceso p = listaEspera.remove(0);
            p.setTiempoPrimeraEjecucion(tiempoActual);

            tiempoActual = ejecutarProceso(p, tiempoActual, lista, listaEspera, mapeoFinal);

        }

       impresionFinal(tiempoActual);
        return mapeoFinal;
    }

    private void verificarLlegadas(List<Proceso> lista, List<Proceso> listaEspera, int tiempoActual) {
        while (!lista.isEmpty() && lista.get(0).getTiempoDeLlegada() <= tiempoActual) {
            Proceso actual = lista.remove(0);
            listaEspera.add(actual);
            mostrarEncabezadoTiempo(tiempoActual);
            mostrarLlegadaProceso(actual);
            espera();
        }
    }


    private int ejecutarProceso(Proceso p, int tiempoActual, List<Proceso> lista, List<Proceso> listaEspera, List<String> mapeoFinal) {
        for (int i = 0; i < p.getDuracion(); i++) {
            verificarLlegadas(lista, listaEspera, tiempoActual);
            mostrarEncabezadoTiempo(tiempoActual);
            System.out.println("Ejecutando " + p.getNombre());
            System.out.println("Ráfagas restantes: " + (p.getDuracion() - i - 1));
            mapeoFinal.add(p.getNombre());
            p.ejecutar(1);
            imprimirColaDeListos(listaEspera);
            tiempoActual++;
            espera();

        }
        revisionProceso(p, tiempoActual);
        espera();
        return tiempoActual;
    }



}
