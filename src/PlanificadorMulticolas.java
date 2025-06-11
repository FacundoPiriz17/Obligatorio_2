import java.util.*;

public class PlanificadorMulticolas extends Planificador {
    private int quantum;

    public PlanificadorMulticolas(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }

    @Override
    public List<String> ejecutar() {
        List<Proceso> tiempoReal = new ArrayList<>();
        List<Proceso> interactivos = new ArrayList<>();
        List<Proceso> batch = new ArrayList<>();
        List<String> mapeofinal = new ArrayList<>();
        List<String> mapeoFCFS = new ArrayList<>();
        List<String> mapeoRR = new ArrayList<>();
        List<String> mapeoSJF = new ArrayList<>();

        for (Proceso p : procesos) {
            if (p.getPrioridad() >= 0 && p.getPrioridad() <= 5) {
                tiempoReal.add(p.clonar());
            } else if (p.getPrioridad() <= 10) {
                interactivos.add(p.clonar());
            } else {
                batch.add(p.clonar());
            }
        }

        System.out.println("Planificación Multicolas");

        if (!tiempoReal.isEmpty()) {
            mapeoFCFS = new PlanificadorFCFS(tiempoReal).ejecutar();
        }

        if (!interactivos.isEmpty()) {
            mapeoRR = new PlanificadorRoundRobin(interactivos, quantum).ejecutar();
        }

        if (!batch.isEmpty()) {
           mapeoSJF = new PlanificadorSJF(batch).ejecutar();
        }

        System.out.println("\nFin de planificación multicolas");
        for (String p : mapeoFCFS) {
            mapeofinal.add(p);
        }
        for (String p : mapeoRR) {
            mapeofinal.add(p);
        }
        for (String p : mapeoSJF) {
            mapeofinal.add(p);
        }
        System.out.println(mapeofinal);

        return mapeofinal;
    }
}