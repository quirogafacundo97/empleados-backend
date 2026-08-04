package Jar.dto;

import java.util.List;

public class DepartamentoConEmpleadosDTO {
    private Long id;
    private String nombre;
    private List<EmpleadoResponseDTO> empleados;

    public DepartamentoConEmpleadosDTO(){}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setEmpleados(List<EmpleadoResponseDTO> empleados){
        this.empleados = empleados;
    }

    public List<EmpleadoResponseDTO> getEmpleados(){
        return empleados;
    }
}
