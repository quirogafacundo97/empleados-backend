package Jar.exception;



public class EmpleadoNoEncontradoException extends RuntimeException{
    public EmpleadoNoEncontradoException(Long id){
        super("No existe el empleado con id: " + id);
    }

    public EmpleadoNoEncontradoException(String criterio, String valor){
        super("No se encontraron empleados con " + criterio + ": " + valor);
    }

}
