import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Proceso> listaProcesos3 = Arrays.asList(
                new Proceso("P1", 0, 7),
                new Proceso("P2", 0, 6),
                new Proceso("P3", 3, 2),
                new Proceso("P4", 7, 6),
                new Proceso("P5", 8, 1)
        );
        new PlanificadorFCFS(clonar(listaProcesos3)).ejecutar();
        System.out.println();
        new PlanificadorSJF(clonar(listaProcesos3)).ejecutar();
        System.out.println();
        new PlanificadorSRTF(clonar(listaProcesos3)).ejecutar();
    }

    private static List<Proceso> clonar(List<Proceso> original) {
        List<Proceso> copia = new ArrayList<>();
        for (Proceso p : original) copia.add(p.clonar());
        return copia;
    }
}