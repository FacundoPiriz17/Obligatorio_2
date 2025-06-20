public class Proceso {
    private String nombre;
    private int tiempoDeLlegada;
    private int duracion;
    private int tiempoRestante;

    private int prioridad;

    private int tiempoPrimeraEjecucion = -1;

    private int tiempoDeFinalizacion = 0;

    private int tiempoEspera = 0;
    private int tiempoDeRetorno = 0;
    private int tiempoDeRespuesta = 0;

    public Proceso(String nombre, int tiempoLlegada, int duracion) {
        this(nombre, tiempoLlegada, duracion, 0);
    }

    public Proceso(String nombre, int tiempoDeLlegada, int duracion, int prioridad) {
        this.nombre = nombre;
        this.tiempoDeLlegada = tiempoDeLlegada;
        this.duracion = duracion;
        this.tiempoRestante = duracion;
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoDeLlegada() {
        return tiempoDeLlegada;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoDeEspera() {
        return tiempoEspera;
    }

    public int getTiempoDeRetorno() {
        return tiempoDeRetorno;
    }

    public int getTiempoDeFinalizacion() {
        return tiempoDeFinalizacion;
    }

    public int getTiempoDeRespuesta() {
        return tiempoDeRespuesta;
    }

    public void ejecutar(int tiempoDeEjecucion) { // ejecuta el proceso durante un tiempo determinado
        tiempoRestante = Math.max(0, tiempoRestante - tiempoDeEjecucion);
    }

    public boolean ejecucionFinalizada() { // verifica si su ejecucion termino
        return tiempoRestante == 0;
    }

    public Proceso clonar() { // clona el proceso
        return new Proceso(nombre, tiempoDeLlegada, duracion, prioridad);
    }

    public void setTiempoDeRespuesta() { // se calcula el tiempo de respuesta
        tiempoDeRespuesta = tiempoPrimeraEjecucion - tiempoDeLlegada;
    }

    public int getTiempoPrimeraEjecucion() {
        return tiempoPrimeraEjecucion;
    }

    public void setTiempoPrimeraEjecucion(int tiempo) { // se calcula el tiempo de la primera ejecucion
        if (tiempoPrimeraEjecucion == -1) {
            tiempoPrimeraEjecucion = tiempo;
            setTiempoDeRespuesta();
        }
    }

    public void setTiempoDeEspera() { // se calcula el tiempo de espera del proceso
        this.tiempoEspera = tiempoDeFinalizacion - tiempoDeLlegada - duracion;
    }

    public void setTiempoDeFinalizacion(int tiempo) {
        tiempoDeFinalizacion = tiempo;
    }

    public void setTiempoDeRetorno() { // se calcula el tiempo de retorno del proceso
        tiempoDeRetorno = tiempoDeFinalizacion - tiempoDeLlegada;
    }

}
