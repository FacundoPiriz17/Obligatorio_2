import java.util.*;

public class PlanificadorMulticolas extends Planificador {
    private int quantum;

    public PlanificadorMulticolas(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
    }

    @Override
    public void ejecutar() {
        List<Proceso> lista = new ArrayList<>(procesos);
        lista.sort(Comparator.comparingInt(Proceso::getTiempoDeLlegada));
        int tiempoActual = 0;
        int index = 0;

        Queue<Proceso> tiempoReal = new LinkedList<>();
        Queue<Proceso> interactivo = new LinkedList<>();
        Queue<Proceso> batch = new LinkedList<>();
    }
}