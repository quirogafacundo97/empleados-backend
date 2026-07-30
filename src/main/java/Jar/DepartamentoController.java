package Jar;
import Jar.dto.DepartamentoRequestDTO;
import Jar.dto.DepartamentoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import java.util.List;

@Tag(
        name = "Departamentos",
        description = "Operaciones relacionadas con la gestión de departamentos"
)
@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @Operation(
            summary = "Crear un departamento",
            description = "Crea un departamento"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> crearDepartamento(@Valid @RequestBody DepartamentoRequestDTO requestDTO){
        DepartamentoResponseDTO responseDTO = departamentoService.guardarDepartamento(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(
            summary = "Obtener un departamento",
            description = "Obtiene un departamento a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> obtenerDepartamento(@Parameter(description = "ID del departamento", example = "3") @PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.obtenerDepartamentoPorId(id));
    }

    @Operation(
            summary = "Eliminar un departamento",
            description = "Elimina un departamento por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Departamento inexistente"),
            @ApiResponse(responseCode = "204", description = "Departamento eliminado correctamente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDepartamento(@Parameter(description = "ID del Departamento a eliminar", example = "1")@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Actualizar un departamento",
            description = "Actualiza un departamento existente a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Departamento a actualizar inexistente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> actualizarDepartamento(@Valid @RequestBody DepartamentoRequestDTO requestDTO, @Parameter(description = "ID del departamento a actualizar")@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.actualizarDepartamento(id, requestDTO));
    }
    @Operation(
            summary = "Listar departamentos",
            description = "Obtiene todos los departamentos cargados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> obtenerDepartamentos() {
        return ResponseEntity.ok(departamentoService.obtenerDepartamentos());
    }

}
