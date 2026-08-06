package Jar;


import Jar.dto.EmpleadoRequestDTO;
import Jar.dto.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @ApiResponse(
            responseCode = "404",
            description = "Empleado no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                    value= """
                    {
                        "status": 404,
                        "message": "No existe el empleado con el id: 10",
                        "timestamp": "2026-07-31T01:50:00",
                        "path": "/api/v1/empleados/10"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "200",
            description = "Empleado encontrado"
        )
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
        @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "status": 400,
                            "message": "Error de validacion en los campos enviados",
                            "timestamp": "2026-07-31T01:50:00",
                            "path": "/api/v1/empleados",
                            "details": {
                                "nombre": "El nombre es obligatorio"
                            }
                        }
                        """
                    )
                )
            ),
        @ApiResponse(
                responseCode = "404",
                description = "Departamento no encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                        value= """
                            {
                                "status": 404,
                                "message": "No existe el departamento con el id: 10",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            )
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
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                            "status": 400,
                            "message": "Error de validacion en los campos enviados",
                            "timestamp": "2026-07-31T01:50:00",
                            "path": "/api/v1/empleados",
                            "details": {
                                "nombre": "El nombre es obligatorio"
                            }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empleado no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class),
                            examples = @ExampleObject(
                                    value= """
                            {
                                "status": 404,
                                "message": "No existe el empleado con el id: 10",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                            )
                    )
            )
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Empleado no encontrado",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDTO.class),
                        examples = @ExampleObject(
                            value= """
                            {
                                "status": 404,
                                "message": "No existe el empleado con el id: 10",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            ),
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Departamento no encontrado",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDTO.class),
                        examples = @ExampleObject(
                            value= """
                            {
                                "status": 404,
                                "message": "No existe el departamento: Marketing",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            ),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente", content = @Content(mediaType = "application/json"))
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
            @ApiResponse(
                responseCode = "200",
                description = "Lista de empleados obtenida correctamente"),

            @ApiResponse(
                responseCode = "404",
                description = "No existen apellidos con el prefijo ingresado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                        value= """
                            {
                                "status": 404,
                                "message": "No se encontraron empleados cuyo apellido comienza con: Mar",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                    )
                )

            )

    })
    //Obtener empleados por un apellido que tenga un determinado prefijo
    @GetMapping("/apellido/prefijo")
    public ResponseEntity<Page<EmpleadoResponseDTO>>  obtenerEmpleadosPorPrefijo(@Parameter(description = "Prefijo de apellido", example = "Qui")@RequestParam String prefijo, @ParameterObject @PageableDefault(page = 0,size = 3, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleados = empleadoService.apellidoStartingWith(prefijo, pageable);
        return ResponseEntity.ok(empleados);
    }

    @Operation(
            summary = "Empleados por palabra en apellido",
            description = "Buscar empleados cuyo apellido contengan una cadena buscada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de empleados obtenida correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron empleados cuyo apellido contenga la palabra ingresada",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDTO.class),
                        examples = @ExampleObject(
                            value= """
                            {
                                "status": 404,
                                "message": "No se encontraron empleados con apellido que contenga: rez",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            )

    })
    @GetMapping("/apellido/contiene")
    public ResponseEntity<Page<EmpleadoResponseDTO>> obtenerEmpleadosApellidoContaining(@Parameter(description = "Palabra que contenga el apellido", example = "rod")@RequestParam String contienePalabra, @ParameterObject @PageableDefault(page = 0,size = 3, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleados = empleadoService.apellidoContaining(contienePalabra, pageable);
        return ResponseEntity.ok(empleados);
    }

    @Operation(
            summary = "Empleados por puesto",
            description = "Buscar empleados por puesto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "404",
                    description = "Puesto no encontrado",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDTO.class),
                        examples = @ExampleObject(
                            value= """
                            {
                                "status": 404,
                                "message": "No se encontraron empleados con el puesto: Frontend",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            ),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    // Obtener empleados por puesto usando RequestParam
    @GetMapping("/buscar/puesto")
    public ResponseEntity<Page<EmpleadoResponseDTO>> buscarEmpleadosPorPuesto(@Parameter(description = "Nombre del puesto", example = "Junior Backend")@RequestParam String puesto, @ParameterObject @PageableDefault(page = 0,size = 3, sort = "puesto", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleadoService.buscarPorPuesto(puesto, pageable);
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Apellido no encontrado",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDTO.class),
                        examples = @ExampleObject(
                            value= """
                            {
                                "status": 404,
                                "message": "No se encontraron empleados con el apellido: Gimenez",
                                "timestamp": "2026-07-31T01:50:00",
                                "path": "/api/v1/empleados/10"
                            }
                            """
                        )
                    )
            ),
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente")
    })
    @GetMapping("/apellido")
    public ResponseEntity<Page<EmpleadoResponseDTO>> buscarEmpleadosPorApellido(@Parameter(description = "Apellido de empleados a buscar", example = "Quiroga")@RequestParam String apellido, @ParameterObject @PageableDefault(page = 0,size = 3, sort = "apellido", direction = Sort.Direction.ASC) Pageable pageable){
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleadoService.obtenerEmpleadoPorApellido(apellido, pageable);
        return ResponseEntity.ok(empleadoResponseDTOS);
    }
}
