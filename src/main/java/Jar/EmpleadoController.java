package Jar;

import java.util.List;

import Jar.dto.EmpleadoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import Jar.dto.EmpleadoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.web.PageableDefault;

@Tag(
        name = "Empleados",
        description = "Operaciones relacionadas con la gestión de empleados"
)
@RestController
@RequestMapping("/api/v1/empleados") //definir una ruta base con version

public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }


    //Obtener Empleados por id

    @Operation(
            summary = "Obtener un empleado",
            description = "Obtiene un empleado a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "200", description = "Empleado encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerEmpleadoPorId(@Parameter(description = "ID del empleado", example = "5") @PathVariable Long id){
        EmpleadoResponseDTO empleadoResponseDTO = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleadoResponseDTO);
    }

    @Operation(
            summary = "Crear un empleado",
            description = "Crea un empleado y lo asocia a un departamento existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado")
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crearEmpleado(@Valid @RequestBody EmpleadoRequestDTO requestDTO){
        EmpleadoResponseDTO empleadoResponseDTO = empleadoService.guardarEmpleado(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoResponseDTO);
    }

    @Operation(
            summary = "Actualizar un empleado",
            description = "Actualiza un empleado existente a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado o departamento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizarEmpleadoPorId(@Parameter(description = "ID del empleado a actualizar", example = "5")@PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO requestDTO){
        EmpleadoResponseDTO empleadoResponseDTO = empleadoService.actualizarEmpleado(id, requestDTO);
        return ResponseEntity.ok(empleadoResponseDTO);
    }

    @Operation(
            summary = "Eliminar un empleado",
            description = "Eliminar un empleado a partir de su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Empleado a eliminar no encontrado"),
            @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleadoPorId(@Parameter(description = "ID del empleado a eliminar", example = "5")@PathVariable Long id){
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Empleados por departamento",
            description = "Buscar empleados por nombre de departamento"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    //Obtener empleados por nombre de departamento
    @GetMapping("/departamento")
    public ResponseEntity<Page<EmpleadoResponseDTO>> obtenerEmpleadosPorDepartamento(@Parameter(description = "Nombre del departamento", example = "Marketing")@RequestParam String nombre, @ParameterObject @PageableDefault(page = 0,size = 5, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleadosDTOS = empleadoService.buscarEmpleadoPorDepartamento(nombre, pageable);
        return ResponseEntity.ok(empleadosDTOS);
    }

    @Operation(
            summary = "Empleados por prefijo",
            description = "Obtener empleados por prefijo en el apellido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    //Obtener empleados por un apellido que tenga un determinado prefijo
    @GetMapping("/apellido/prefijo")
    public ResponseEntity<List<EmpleadoResponseDTO>>  obtenerEmpleadosPorPrefijo(@Parameter(description = "Prefijo de apellido", example = "Qui")@RequestParam String prefijo){
        List<EmpleadoResponseDTO> empleados = empleadoService.apellidoStartingWith(prefijo);
        return ResponseEntity.ok(empleados);
    }

    @Operation(
            summary = "Empleados por palabra en apellido",
            description = "Buscar empleados cuyo apellido contengan una cadena buscada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    @GetMapping("/apellido/contiene")
    public ResponseEntity<List<EmpleadoResponseDTO>> obtenerEmpleadosApellidoContaining(@Parameter(description = "Palabra que contenga el apellido", example = "rod")@RequestParam String contienePalabra){
        List<EmpleadoResponseDTO> empleados = empleadoService.apellidoContaining(contienePalabra);
        return ResponseEntity.ok(empleados);
    }

    @Operation(
            summary = "Empleados por puesto",
            description = "Buscar empleados por puesto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "No hay empleados en ese puesto"),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    // Obtener empleados por puesto usando RequestParam
    @GetMapping("/buscar/puesto")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarEmpleadosPorPuesto(@Parameter(description = "Nombre del puesto", example = "Junior Backend")@RequestParam String puesto ){
        List<EmpleadoResponseDTO> empleadoResponseDTOS = empleadoService.buscarPorPuesto(puesto);
        return ResponseEntity.ok(empleadoResponseDTOS);
    }

    //Obtener todos los Empleados
    @Operation(
            summary = "Listar empleados",
            description = "Obtiene todos los empleados cargados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<Page<EmpleadoResponseDTO>> listarTodosLosEmpleados(@ParameterObject @PageableDefault(page = 0, size = 5, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleadoService.buscarTodosLosEmpleados(pageable);
        return ResponseEntity.ok(empleadoResponseDTOS);
    }

    @Operation(
            summary = "Empleados por apellido",
            description = "Buscar empleados por apellido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "No hay empleados con el apellido solicitado"),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    @GetMapping("/apellido")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarEmpleadosPorApellido(@Parameter(description = "Apellido de empleados a buscar", example = "Quiroga")@RequestParam String apellido){
        List<EmpleadoResponseDTO> empleadoResponseDTOS = empleadoService.obtenerEmpleadoPorApellido(apellido);
        return ResponseEntity.ok(empleadoResponseDTOS);
    }
}
