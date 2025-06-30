import java.util.*;

public class PlanificadorMulticolas extends Planificador {
    private int quantum;
    private List<Proceso> listaFinal;
    private int tiempoGlobal;

    public PlanificadorMulticolas(List<Proceso> procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
        this.listaFinal = new LinkedList<>();
        this.tiempoGlobal = 0;
    }

    @Override
    public List<String> ejecutar() {
        List<Proceso> pendientesTiempoReal = new LinkedList<>();
        List<Proceso> pendientesInteractivos = new LinkedList<>();
        List<Proceso> pendientesBatch = new LinkedList<>();

        List<Proceso> colaTiempoReal = new LinkedList<>();
        List<Proceso> colaInteractivos = new LinkedList<>();
        List<Proceso> colaBatch = new LinkedList<>();

        List<String> mapeoFinal = new ArrayList<>();

        // Clonar procesos y distribuir según prioridad
        for (Proceso p : procesos) {
            Proceso copia = p.clonar();
            if (p.getPrioridad() >= -20 && p.getPrioridad() <= 0) {
                pendientesTiempoReal.add(copia);
            } else if (p.getPrioridad() <= 19) {
                pendientesInteractivos.add(copia);
            } else {
                pendientesBatch.add(copia);
            }
        }

        Comparator<Proceso> porLlegada = Comparator.comparingInt(Proceso::getTiempoDeLlegada);
        pendientesTiempoReal.sort(porLlegada);
        pendientesInteractivos.sort(porLlegada);
        pendientesBatch.sort(porLlegada);

        while (!pendientesTiempoReal.isEmpty() || !pendientesInteractivos.isEmpty() || !pendientesBatch.isEmpty()
                || !colaTiempoReal.isEmpty() || !colaInteractivos.isEmpty() || !colaBatch.isEmpty()) {

            // Verificar llegadas en cada categoría
            verificarLlegadas(pendientesTiempoReal, colaTiempoReal);
            verificarLlegadas(pendientesInteractivos, colaInteractivos);
            verificarLlegadas(pendientesBatch, colaBatch);

            boolean ejecutado = false;

            // TIEMPO REAL
            if (!colaTiempoReal.isEmpty()) {
                Proceso p = colaTiempoReal.remove(0);
                if (p.getTiempoPrimeraEjecucion() == -1) {
                    p.setTiempoPrimeraEjecucion(tiempoGlobal);
                }
                while (!p.ejecucionFinalizada()) {
                    mostrarEncabezadoTiempo(tiempoGlobal);
                    System.out.println("Ejecutando " + p.getNombre() + " (TIEMPO REAL)");
                    imprimirColasDeListos(colaInteractivos, colaBatch, colaTiempoReal);
                    mapeoFinal.add(p.getNombre());
                    p.ejecutar(1);
                    tiempoGlobal++;
                    verificarLlegadas(pendientesTiempoReal, colaTiempoReal);
                    verificarLlegadas(pendientesInteractivos, colaInteractivos);
                    verificarLlegadas(pendientesBatch, colaBatch);
                    espera();
                }
                finalizarProceso(p, tiempoGlobal);
                ejecutado = true;
            }

            // INTERACTIVOS (RR con Quantum)
            if (!ejecutado && !colaInteractivos.isEmpty()) {
                Proceso p = colaInteractivos.remove(0);
                if (p.getTiempoPrimeraEjecucion() == -1) {
                    p.setTiempoPrimeraEjecucion(tiempoGlobal);
                }

                int ejecutadoQuantum = 0;
                boolean interrumpido = false;

                while (ejecutadoQuantum < quantum && !p.ejecucionFinalizada()) {
                    verificarLlegadas(pendientesTiempoReal, colaTiempoReal);
                    verificarLlegadas(pendientesInteractivos, colaInteractivos);
                    verificarLlegadas(pendientesBatch, colaBatch);

                    // Interrupción por Tiempo Real
                    if (!colaTiempoReal.isEmpty()) {
                        colaInteractivos.add(0, p); // volver al frente
                        interrumpido = true;
                        break; // salir del bucle
                    }

                    mostrarEncabezadoTiempo(tiempoGlobal);
                    System.out.println("Ejecutando " + p.getNombre() + " (INTERACTIVO)");
                    imprimirColasDeListos(colaInteractivos, colaBatch, colaTiempoReal);
                    mapeoFinal.add(p.getNombre());
                    p.ejecutar(1);
                    ejecutadoQuantum++;
                    tiempoGlobal++;
                    espera();
                }

                //Solo se marca como ejecutado si no fue interrumpido
                if (!interrumpido) {
                    if (p.ejecucionFinalizada()) {
                        finalizarProceso(p, tiempoGlobal);
                    } else if (ejecutadoQuantum == quantum) {
                        colaInteractivos.add(p);
                    }
                    ejecutado = true;
                }
            }

            // BATCH (SJF)
            if (!ejecutado && !colaBatch.isEmpty()) {
                colaBatch.sort(Comparator.comparingInt(Proceso::getTiempoRestante));
                Proceso p = colaBatch.remove(0);
                if (p.getTiempoPrimeraEjecucion() == -1) {
                    p.setTiempoPrimeraEjecucion(tiempoGlobal);
                }

                boolean interrumpido = false;
                while (!p.ejecucionFinalizada()) {
                    verificarLlegadas(pendientesTiempoReal, colaTiempoReal);
                    verificarLlegadas(pendientesInteractivos, colaInteractivos);
                    verificarLlegadas(pendientesBatch, colaBatch);
                    // Interrupción por Tiempo Real o Interactivo
                    if (!colaTiempoReal.isEmpty() || !colaInteractivos.isEmpty()) {
                        colaBatch.add(0, p); // volver al frente
                        interrumpido = true;
                        break;
                    }
                    mostrarEncabezadoTiempo(tiempoGlobal);
                    System.out.println("Ejecutando " + p.getNombre() + " (BATCH)");
                    imprimirColasDeListos(colaInteractivos, colaBatch, colaTiempoReal);
                    mapeoFinal.add(p.getNombre());
                    p.ejecutar(1);
                    tiempoGlobal++;
                    espera();
                }
                if (!interrumpido) {
                    finalizarProceso(p, tiempoGlobal);
                }
                ejecutado = true;
            }

            // CPU inactiva
            if (!ejecutado) {
                // Verifica primero si llega algo justo ahora
                verificarLlegadas(pendientesTiempoReal, colaTiempoReal);
                verificarLlegadas(pendientesInteractivos, colaInteractivos);
                verificarLlegadas(pendientesBatch, colaBatch);

                if (!colaTiempoReal.isEmpty()) {
                    // No está realmente inactiva: entró algo de tiempo real
                    continue; // volvemos al inicio del while
                }

                mostrarEncabezadoTiempo(tiempoGlobal);
                mostrarCpuInactiva(tiempoGlobal);
                tiempoGlobal++;
                espera();
            }

        }

        procesos.clear();
        procesos.addAll(listaFinal);
        procesos.sort(Comparator.comparing(Proceso::getNombre));
        System.out.println("-----------------------------------");
        System.out.println();
        return mapeoFinal;
    }

    private void verificarLlegadas(List<Proceso> pendientes, List<Proceso> colaListos) {
        while (!pendientes.isEmpty() && pendientes.get(0).getTiempoDeLlegada() <= tiempoGlobal) {
            Proceso nuevo = pendientes.remove(0);
            mostrarEncabezadoTiempo(tiempoGlobal);
            mostrarLlegadaProceso(nuevo);
            colaListos.add(nuevo);
            espera();
        }
    }


    private void finalizarProceso(Proceso p, int tiempoFinal) {
        p.setTiempoDeFinalizacion(tiempoFinal);
        p.setTiempoDeRetorno();
        p.setTiempoDeEspera();
        p.setTiempoDeRespuesta();
        listaFinal.add(p);
    }

    public List<Proceso> getListaFinal() {
        return listaFinal;
    }

    private void imprimirColasDeListos(Collection<Proceso> interactivos, Collection<Proceso> batch, Collection<Proceso> tiempoReal) {
        System.out.println("Cola de listos (TIEMPO REAL): " + tiempoReal.stream().map(Proceso::getNombre).toList());
        System.out.println("Cola de listos (INTERACTIVOS): " + interactivos.stream().map(Proceso::getNombre).toList());
        System.out.println("Cola de listos (BATCH): " + batch.stream().map(Proceso::getNombre).toList());
    }

}
