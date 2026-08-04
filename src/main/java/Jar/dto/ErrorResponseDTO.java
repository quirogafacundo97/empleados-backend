package Jar.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

@Schema(description = "Respuesta de error de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {

    @Schema(description = "Codigo HTTP", example = "404")
    private int status;
    @Schema(description = "Mensaje descriptivo del error", example = "Descripcion del error")
    private String message;
    @Schema(description = "Fecha y hora en que ocurrió el error", example = "2026-07-30T00:05:34")
    private LocalDateTime timestamp;
    @Schema(description = "Ruta del endpoint donde ocurrio el error", example = "/api/v1/empleados/5")
    private String path;
    @Schema(description = "Detalles del error")
    private Map<String, String> details;

    public ErrorResponseDTO(){}

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void setDetails(Map<String, String> details){
        this.details = details;
    }

    public Map<String, String> getDetails(){
        return details;
    }
}

