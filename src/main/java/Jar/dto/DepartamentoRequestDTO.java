package Jar.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartamentoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, message = "El nombre debe tener por lo menos 2 caracteres")
    @Schema(
            description = "Nombre del departamento",
            example = "Marketing"
    )
    private String nombre;

    public DepartamentoRequestDTO(){}

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

}
