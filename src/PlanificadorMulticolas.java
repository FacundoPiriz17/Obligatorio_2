import java.util.*;

public class PlanificadorMulticolas extends Planificador {
    private int quantum;

    private List<Proceso> listaFinal;

    public PlanificadorMulticolas(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
        listaFinal = new LinkedList<>();
    }

    @Override
    public List<String> ejecutar() { // ejecuta el planificador multicolas
        List<Proceso> tiempoReal = new ArrayList<>();
        List<Proceso> interactivos = new ArrayList<>();
        List<Proceso> batch = new ArrayList<>();

        List<String> mapeofinal = new ArrayList<>();
        List<String> mapeoFCFS = new ArrayList<>();
        List<String> mapeoRR = new ArrayList<>();
        List<String> mapeoSJF = new ArrayList<>();

        for (Proceso p : procesos) {
            if (p.getPrioridad() >= -20 && p.getPrioridad() <= 0) {
                tiempoReal.add(p.clonar());
            } else if (p.getPrioridad() <= 19) {
                interactivos.add(p.clonar());
            } else {
                batch.add(p.clonar());
            }
        }

        Planificador planificadorRoundRobin = new PlanificadorRoundRobin(interactivos, quantum);
        Planificador planificadorFCFS = new PlanificadorFCFS(tiempoReal);
        Planificador planificadorSJF = new PlanificadorSJF(batch);

        System.out.println("Planificación Multicolas");

        if (!tiempoReal.isEmpty()) {
            mapeoFCFS = planificadorFCFS.ejecutar();
        }

        if (!interactivos.isEmpty()) {
            mapeoRR = planificadorRoundRobin.ejecutar();
        }

        if (!batch.isEmpty()) {
            mapeoSJF = planificadorSJF.ejecutar();
        }

        System.out.println("\nFin de planificación multicolas");
        System.out.println();
        System.out.println("Lista de ejecuciones del planificador de procesos interactivos: ");
        System.out.println(mapeoFCFS);
        planificadorFCFS.mostrarMatriz();
        System.out.println("Lista de ejecuciones del planificador de procesos tiempo real: ");
        System.out.println(mapeoRR);
        planificadorRoundRobin.mostrarMatriz();
        System.out.println("Lista de ejecuciones del planificador de procesos batch: ");
        System.out.println(mapeoSJF);
        planificadorSJF.mostrarMatriz();
        this.listaFinal.addAll(planificadorFCFS.getProcesos());
        this.listaFinal.addAll(planificadorRoundRobin.getProcesos());
        this.listaFinal.addAll(planificadorSJF.getProcesos());
        return mapeofinal;
    }

    public List<Proceso> getListaFinal() {
        return listaFinal;
    }
}