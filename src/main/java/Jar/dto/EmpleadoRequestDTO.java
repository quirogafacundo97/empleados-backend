package Jar.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class EmpleadoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, message = "El nombre debe tener a menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 2, message = "El apellido debe tener a menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "El puesto no puede estar vacio")
    private String puesto;

    @NotNull(message = "Debe indicar un departamento")
    private Long departamentoId;

    public EmpleadoRequestDTO(){}

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getPuesto(){
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

}
