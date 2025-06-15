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

    public void ejecutar(int tiempoDeEjecucion) {
        tiempoRestante = Math.max(0, tiempoRestante - tiempoDeEjecucion);
    }

    public boolean ejecucionFinalizada() {
        return tiempoRestante == 0;
    }

    public void aumentarTiempoDeEspera() {
        tiempoEspera += 1;
    }

    public Proceso clonar() {
        return new Proceso(nombre, tiempoDeLlegada, duracion, prioridad);
    }
    public void setTiempoDeRespuesta() {
        tiempoDeRespuesta = tiempoPrimeraEjecucion - tiempoDeLlegada;
    }

    public int getTiempoPrimeraEjecucion() {
        return tiempoPrimeraEjecucion;
    }

    public void setTiempoPrimeraEjecucion(int tiempo) {
        if (tiempoPrimeraEjecucion == -1) {
            tiempoPrimeraEjecucion = tiempo;
            setTiempoDeRespuesta();
        }
    }
    public void setTiempoDeEspera() {
        this.tiempoEspera = tiempoDeFinalizacion - tiempoDeLlegada - duracion;
    }

    public void setTiempoDeFinalizacion(int tiempo) {
        tiempoDeFinalizacion = tiempo;
    }

    public void setTiempoDeRetorno() {
        tiempoDeRetorno = tiempoDeFinalizacion - tiempoDeLlegada;
    }

}
