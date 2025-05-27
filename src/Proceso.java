public class Proceso {
    private String nombre;
    private int tiempoDeLlegada;
    private int duracion;
    private int tiempoRestante;
    private int prioridad;

    private String estado;

    public Proceso(String nombre, int tiempoLlegada, int duracion) {
        this(nombre, tiempoLlegada, duracion, 0);
    }

    public Proceso(String nombre, int tiempoDeLlegada, int duracion, int prioridad) {
        this.nombre = nombre;
        this.tiempoDeLlegada = tiempoDeLlegada;
        this.duracion = duracion;
        this.tiempoRestante = duracion;
        this.prioridad = prioridad;
        this.estado = "Creación";
    }

    public String getNombre() { return nombre; }
    public int getTiempoDeLlegada() { return tiempoDeLlegada; }
    public int getDuracion() { return duracion; }
    public int getTiempoRestante() { return tiempoRestante; }
    public int getPrioridad() { return prioridad; }

    public void ejecutar(int tiempoDeEjecucion) {
        tiempoRestante = Math.max(0, tiempoRestante - tiempoDeEjecucion);
    }

    public boolean EnEjecucion() {
        return tiempoRestante == 0;
    }

    public Proceso clonar() {
        return new Proceso(nombre, tiempoDeLlegada, duracion, prioridad);
    }

}
