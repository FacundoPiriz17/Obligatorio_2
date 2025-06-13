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

                List<Proceso> listaProcesos3 = Arrays.asList(
                                new Proceso("P1", 0, 6, 2),
                                new Proceso("P2", 2, 4, 1),
                                new Proceso("P3", 4, 2, 3),
                                new Proceso("P4", 6, 5, 2));

                List<Proceso> listaProcesos4 = Arrays.asList(
                                new Proceso("P1", 0, 5, 2),
                                new Proceso("P2", 1, 4, 1),
                                new Proceso("P3", 2, 3, 12),
                                new Proceso("P4", 3, 2, 4),
                                new Proceso("P5", 4, 6, 8),
                                new Proceso("P6", 6, 5, 0));

                List<Proceso> listaProcesos5 = Arrays.asList(
                                new Proceso("P1", 0, 5, 2),
                                new Proceso("P2", 1, 4, 1),
                                new Proceso("P3", 2, 3, 12),
                                new Proceso("P4", 3, 2, 4),
                                new Proceso("P5", 4, 6, 8),
                                new Proceso("P6", 6, 5, 0));

                Planificador planificadorFCFS = new PlanificadorFCFS(clonar(listaProcesos));
                List<String> ejecucionFCFS = planificadorFCFS.ejecutar();
                ImprimirResultados(planificadorFCFS, ejecucionFCFS);

                planificadorFCFS.mostrarMatriz();
                /*
                 * Planificador planificadorSJF = new PlanificadorSJF(clonar(listaProcesos));
                 * List<String> ejecucionSJF = planificadorSJF.ejecutar();
                 * ImprimirResultados(planificadorSJF, ejecucionSJF);
                 * 
                 * Planificador planificadorSRTF = new PlanificadorSRTF(clonar(listaProcesos));
                 * List<String> ejecucionSRTF = planificadorSRTF.ejecutar();
                 * ImprimirResultados(planificadorSRTF, ejecucionSRTF);
                 * 
                 * Planificador planificadorRR = new
                 * PlanificadorRoundRobin(clonar(listaProcesos2),3);
                 * List<String> ejecucionRR = planificadorRR.ejecutar();
                 * ImprimirResultados(planificadorRR, ejecucionRR);
                 * 
                 * Planificador planificadorPrioridadesExpropiativo = new
                 * PlanificadorPrioridades(clonar(listaProcesos4),true);
                 * List<String> ejecucionPrioridadesExpropiativo = planificadorRR.ejecutar();
                 * ImprimirResultados(planificadorPrioridadesExpropiativo,
                 * ejecucionPrioridadesExpropiativo);
                 * 
                 * Planificador planificadorPrioridadesNoExpropiativo = new
                 * PlanificadorPrioridades(clonar(listaProcesos4),false);
                 * List<String> ejecucionPrioridadesNoExpropiativo =
                 * planificadorPrioridadesNoExpropiativo.ejecutar();
                 * ImprimirResultados(planificadorPrioridadesNoExpropiativo,
                 * ejecucionPrioridadesNoExpropiativo);
                 * 
                 * Planificador planificadorMulticolas = new
                 * PlanificadorMulticolas(clonar(listaProcesos5),3);
                 * List<String> ejecucionMulticolas = planificadorMulticolas.ejecutar();
                 * ImprimirResultados(planificadorMulticolas, ejecucionMulticolas);
                 */
        }

        private static List<Proceso> clonar(List<Proceso> original) {
                List<Proceso> copia = new ArrayList<>();
                for (Proceso p : original)
                        copia.add(p.clonar());
                return copia;
        }

        private static void ImprimirResultados(Planificador planificador, List<String> resultados) {
                System.out.println();
                System.out.println("Lista de ejecución final:");
                System.out.println(resultados);
                System.out.println();
                System.out.println("Tiempos de espera:");
                for (Proceso proceso : planificador.procesos) {
                        System.out.println(proceso.getNombre() + " " + proceso.getTiempoDeEspera());
                }
                System.out.println();
                System.out.println("Tiempos de Retorno:");
                for (Proceso proceso : planificador.procesos) {
                        System.out.println(proceso.getNombre() + " " + proceso.getTiempoDeRetorno());
                }
                System.out.println();

        }
}