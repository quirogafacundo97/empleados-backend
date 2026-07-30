package Jar.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class DepartamentoResponseDTO {
    @Schema(
            description = "Identificador del departamento",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del departamento",
            example = "Ventas"
    )
    private String nombre;

    public DepartamentoResponseDTO(){}

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
}
