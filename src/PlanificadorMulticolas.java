import java.util.*;

public class PlanificadorMulticolas extends Planificador {
    private int quantum;

    public PlanificadorMulticolas(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }

    @Override
    public void ejecutar() {
        List<Proceso> tiempoReal = new ArrayList<>();
        List<Proceso> interactivos = new ArrayList<>();
        List<Proceso> batch = new ArrayList<>();

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
            System.out.println("\n--- Ejecutando Cola Tiempo Real (FCFS) ---");
            new PlanificadorFCFS(tiempoReal).ejecutar();
        }

        if (!interactivos.isEmpty()) {
            System.out.println("\n--- Ejecutando Cola Interactiva (Round Robin) ---");
            new PlanificadorRoundRobin(interactivos, quantum).ejecutar();
        }

        if (!batch.isEmpty()) {
            System.out.println("\n--- Ejecutando Cola Batch (SJF) ---");
            new PlanificadorSJF(batch).ejecutar();
        }

        System.out.println("\n== Fin de planificación multicolas ==");
    }
}