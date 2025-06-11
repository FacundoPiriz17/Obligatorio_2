import java.util.List;

public abstract class Planificador {
    protected List<Proceso> procesos;

    public Planificador(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public abstract List<String> ejecutar();

    protected void setUp(){  //Cambiar el estado de los procesos

    }

    protected void calcularTabla(){

    }
}