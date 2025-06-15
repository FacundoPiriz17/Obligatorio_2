import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlanificadorSJF extends Planificador {
    public PlanificadorSJF(List<Proceso> procesos) {
        super(procesos);
    }
    @Override
    public List<String> ejecutar() {
        List<Proceso> listaDeProcesos = new ArrayList<>(procesos);
        listaDeProcesos.sort((p1, p2) -> Integer.compare(p1.getTiempoDeLlegada(), p2.getTiempoDeLlegada()));
        int tiempoActual = 0;
        List<Proceso> listaEspera = new ArrayList<>();
        List<String> mapeoFinal = new ArrayList<>();

        System.out.println("Planificador SJF:");
        while (!listaEspera.isEmpty() || !listaDeProcesos.isEmpty()) {
            verificarLLegadas(listaDeProcesos, tiempoActual, listaEspera);

            if (listaEspera.isEmpty()) {
                mostrarEncabezadoTiempo(tiempoActual);
                mostrarCpuInactiva(tiempoActual);
                tiempoActual++;
                espera();
                continue;
            }

            Proceso p = listaEspera.remove(0);
            p.setTiempoPrimeraEjecucion(tiempoActual);
            tiempoActual = ejecutarProceso(p,tiempoActual,listaDeProcesos, listaEspera, mapeoFinal);
        }
        impresionFinal(tiempoActual);
        return mapeoFinal;
    }

    private void verificarLLegadas(List <Proceso> listaDeProcesos, int tiempoActual, List <Proceso> listaEspera) {
        while (!listaDeProcesos.isEmpty() && listaDeProcesos.get(0).getTiempoDeLlegada() <= tiempoActual) {
            Proceso actual = listaDeProcesos.remove(0);
            listaEspera.add(actual);
            mostrarEncabezadoTiempo(tiempoActual);
            mostrarLlegadaProceso(actual);
            espera();
        }
    }

    private int ejecutarProceso(Proceso actual, int tiempoActual, List <Proceso>listaDeProcesos , List<Proceso> listaEspera , Collection mapeoFinal) {
        for (int i = 0; i < actual.getDuracion(); i++) {
            mostrarEncabezadoTiempo(tiempoActual);
            System.out.println("Ejecutando " + actual.getNombre());
            System.out.println("Ráfagas restantes: " + (actual.getDuracion() - i - 1));
            mapeoFinal.add(actual.getNombre());
            actual.ejecutar(1);
            imprimirColaDeListos(listaEspera);
            tiempoActual++;
            verificarLLegadas(listaDeProcesos, tiempoActual, listaEspera);
            espera();
        }
        revisionProceso(actual, tiempoActual);
        espera();
        return tiempoActual;
    }
}
