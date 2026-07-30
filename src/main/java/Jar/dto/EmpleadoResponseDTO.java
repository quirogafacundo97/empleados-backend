package Jar.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmpleadoResponseDTO {

    @Schema(
            description = "Identificador del empleado",
            example = "5"
    )
    private Long id;

    @Schema(
            description = "Nombre completo",
            example = "Juan Pérez"
    )
    private String nombreCompleto;

    @Schema(
            description = "Puesto del empleado",
            example = "Backend Developer"
    )
    private String puesto;

    @Schema(
            description = "Nombre del departamento",
            example = "Marketing"
    )
    private String departamento;

    public EmpleadoResponseDTO() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
