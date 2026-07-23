package Jar.exception;

public class DepartamentoNoEncontradoException extends RuntimeException{
    public DepartamentoNoEncontradoException(String nombreDpto){
        super("No existe el departamento: " + nombreDpto);
    }

    public DepartamentoNoEncontradoException(Long id){
        super("No existe el departamento con id: " + id);
    }

}
