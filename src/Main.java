import java.util.Arrays;
import java.util.List;

public class Main {
        public static void main(String[] args) {
                List<Proceso> listaProcesos = Arrays.asList( // lista procesos
                                new Proceso("P1", 0, 7),
                                new Proceso("P2", 0, 6),
                                new Proceso("P3", 3, 2),
                                new Proceso("P4", 7, 6),
                                new Proceso("P5", 8, 1));

                List<Proceso> listaProcesos2 = Arrays.asList( // lista procesos
                                new Proceso("P1", 0, 8),
                                new Proceso("P2", 1, 4),
                                new Proceso("P3", 2, 2),
                                new Proceso("P4", 5, 5));

                List<Proceso> listaProcesos4 = Arrays.asList( // lista procesos
                                new Proceso("P1", 0, 5, 2),
                                new Proceso("P2", 1, 4, 1),
                                new Proceso("P3", 2, 3, 12),
                                new Proceso("P4", 3, 2, 4),
                                new Proceso("P5", 4, 6, 8),
                                new Proceso("P6", 6, 5, 0));

                List<Proceso> listaProcesos5 = Arrays.asList( // lista procesos
                                new Proceso("P1", 0, 5, 2),
                                new Proceso("P2", 1, 4, -5),
                                new Proceso("P3", 2, 3, 25),
                                new Proceso("P4", 3, 2, 10),
                                new Proceso("P5", 4, 6, -15),
                                new Proceso("P6", 5, 5, 30));

                Planificador planificadorFCFS = new PlanificadorFCFS(listaProcesos);
                System.out.println(planificadorFCFS.ejecutar());
                planificadorFCFS.mostrarMatriz();


                Planificador planificadorSJF = new PlanificadorSJF(listaProcesos);
                System.out.println(planificadorSJF.ejecutar());
                planificadorSJF.mostrarMatriz();

                Planificador planificadorSRTF = new PlanificadorSRTF(listaProcesos);
                System.out.println(planificadorSRTF.ejecutar());
                planificadorSRTF.mostrarMatriz();

                Planificador planificadorRR = new PlanificadorRoundRobin(listaProcesos2, 3);
                System.out.println(planificadorRR.ejecutar());
                planificadorRR.mostrarMatriz();

                Planificador planificadorPrioridadesExpropiativo = new PlanificadorPrioridades(listaProcesos4, true);
                System.out.println(planificadorPrioridadesExpropiativo.ejecutar());
                planificadorPrioridadesExpropiativo.mostrarMatriz();

                Planificador planificadorPrioridadesNoExpropiativo = new PlanificadorPrioridades(listaProcesos4, false);
                System.out.println(planificadorPrioridadesNoExpropiativo.ejecutar());
                planificadorPrioridadesNoExpropiativo.mostrarMatriz();

                PlanificadorMulticolas planificadorMulticolas = new PlanificadorMulticolas(listaProcesos5, 3);
                System.out.println(planificadorMulticolas.ejecutar());
                planificadorMulticolas.mostrarMatriz();
        }
}