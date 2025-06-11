import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Proceso> listaProcesos = Arrays.asList(
                new Proceso("P1", 0, 7),
                new Proceso("P2", 0, 6),
                new Proceso("P3", 3, 2),
                new Proceso("P4", 7, 6),
                new Proceso("P5", 8, 1)
        );

        List<Proceso> listaProcesos2 = Arrays.asList(
                new Proceso("P1", 0, 8),
                new Proceso("P2", 1, 4),
                new Proceso("P3", 2, 2),
                new Proceso("P4", 5, 5)
        );

        List<Proceso> listaProcesos3 = Arrays.asList(
                new Proceso("P1", 0, 6, 2),
                new Proceso("P2", 2, 4, 1),
                new Proceso("P3", 4, 2, 3),
                new Proceso("P4", 6, 5, 2)
        );

        List<Proceso> listaProcesos4 = Arrays.asList(
                new Proceso("P1", 0, 5, 2),
                new Proceso("P2", 1, 4, 1),
                new Proceso("P3", 2, 3, 12),
                new Proceso("P4", 3, 2, 4),
                new Proceso("P5", 4, 6, 8),
                new Proceso("P6", 5, 7, 8),
                new Proceso("P7", 3, 4, 12)


        );

        new PlanificadorFCFS(clonar(listaProcesos)).ejecutar();
        System.out.println();
        new PlanificadorSJF(clonar(listaProcesos)).ejecutar();
        System.out.println();
        new PlanificadorSRTF(clonar(listaProcesos)).ejecutar();
        System.out.println();
        new PlanificadorRoundRobin(clonar(listaProcesos2),3).ejecutar();
        System.out.println();
        new PlanificadorPrioridades(clonar(listaProcesos3),true).ejecutar();
        System.out.println();
        new PlanificadorPrioridades(clonar(listaProcesos3),false).ejecutar();
        System.out.println();
        new PlanificadorMulticolas(clonar(listaProcesos4),3).ejecutar();
    }

    private static List<Proceso> clonar(List<Proceso> original) {
        List<Proceso> copia = new ArrayList<>();
        for (Proceso p : original) copia.add(p.clonar());
        return copia;
    }
}