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
                        new Proceso("P5", 8, 1));

                List<Proceso> listaProcesos2 = Arrays.asList(
                                new Proceso("P1", 0, 8),
                                new Proceso("P2", 1, 4),
                                new Proceso("P3", 2, 2),
                                new Proceso("P4", 5, 5));

                List<Proceso> listaProcesos4 = Arrays.asList(
                                new Proceso("P1", 0, 5, 2),
                                new Proceso("P2", 1, 4, 1),
                                new Proceso("P3", 2, 3, 12),
                                new Proceso("P4", 3, 2, 4),
                                new Proceso("P5", 4, 6, 8),
                                new Proceso("P6", 6, 5, 0));

                List<Proceso> listaProcesos5 = Arrays.asList(
                        new Proceso("P1", 0, 5, 2),
                        new Proceso("P2", 1, 4, -5),
                        new Proceso("P3", 2, 3, 25),
                        new Proceso("P4", 3, 2, 10),
                        new Proceso("P5", 4, 6, -15),
                        new Proceso("P6", 5, 5, 30)
                );

              Planificador planificadorFCFS = new PlanificadorFCFS(clonar(listaProcesos));
                List<String> ejecucionFCFS = planificadorFCFS.ejecutar();
                planificadorFCFS.mostrarMatriz();

                Planificador planificadorSJF = new PlanificadorSJF(clonar(listaProcesos));
                List<String> ejecucionSJF = planificadorSJF.ejecutar();
                planificadorSJF.mostrarMatriz();


               Planificador planificadorSRTF = new PlanificadorSRTF(clonar(listaProcesos));
                List<String> ejecucionSRTF = planificadorSRTF.ejecutar();
                planificadorSRTF.mostrarMatriz();

                Planificador planificadorRR = new
                PlanificadorRoundRobin(clonar(listaProcesos2),3);
                List<String> ejecucionRR = planificadorRR.ejecutar();
                planificadorRR.mostrarMatriz();

                Planificador planificadorPrioridadesExpropiativo = new PlanificadorPrioridades(clonar(listaProcesos4),true);
                List<String> ejecucionPrioridadesExpropiativo = planificadorPrioridadesExpropiativo.ejecutar();
                planificadorPrioridadesExpropiativo.mostrarMatriz();

                Planificador planificadorPrioridadesNoExpropiativo = new
                PlanificadorPrioridades(clonar(listaProcesos4),false);
                List<String> ejecucionPrioridadesNoExpropiativo = planificadorPrioridadesNoExpropiativo.ejecutar();
                planificadorPrioridadesNoExpropiativo.mostrarMatriz();

                PlanificadorMulticolas planificadorMulticolas = new PlanificadorMulticolas(clonar(listaProcesos5),3);
                List<String> ejecucionMulticolas = planificadorMulticolas.ejecutar();
                planificadorMulticolas.mostrarMatriz(planificadorMulticolas.getListaFinal());
        }

        private static List<Proceso> clonar(List<Proceso> original) {
                List<Proceso> copia = new ArrayList<>();
                for (Proceso p : original)
                        copia.add(p.clonar());
                return copia;
        }
}