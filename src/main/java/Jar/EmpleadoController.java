package Jar;
import java.util.List;

import Jar.dto.EmpleadoRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import Jar.dto.EmpleadoDTO;

@RestController
@RequestMapping("/api/v1/empleados") //definir una ruta base con version

public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }


    //Obtener Empleados por id
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id){
        EmpleadoDTO empleadoDTO = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleadoDTO);
    }


    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoRequestDTO requestDTO){
        EmpleadoDTO empleadoDTO = empleadoService.guardarEmpleado(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleadoPorId(@PathVariable Long id,@Valid @RequestBody EmpleadoRequestDTO requestDTO){
        EmpleadoDTO empleadoDTO = empleadoService.actualizarEmpleado(id, requestDTO);
        return ResponseEntity.ok(empleadoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleadoPorId(@PathVariable Long id){
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //Obtener empleados por nombre de departamento
    @GetMapping("/departamento")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleadosPorDepartamento(@RequestParam String nombre){
        List<EmpleadoDTO> empleadosDTOS = empleadoService.buscarEmpleadoPorDepartamento(nombre);
        return ResponseEntity.ok(empleadosDTOS);
    }
    //Obtener empleados por un apellido que tenga un determinado prefijo
    @GetMapping("/apellido/prefijo")
    public ResponseEntity<List<EmpleadoDTO>>  obtenerEmpleadosPorPrefijo(@RequestParam String prefijo){
        List<EmpleadoDTO> empleados = empleadoService.apellidoStartingWith(prefijo);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/apellido/contiene")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleadosApellidoContaining(@RequestParam String contienePalabra){
        List<EmpleadoDTO> empleados = empleadoService.apellidoContaining(contienePalabra);
        return ResponseEntity.ok(empleados);
    }

    // Obtener empleados por puesto usando RequestParam
    @GetMapping("/buscar/puesto")
    public ResponseEntity<List<EmpleadoDTO>> buscarEmpleadosPorPuesto(@RequestParam String puesto ){
        List<EmpleadoDTO> empleadoDTOS = empleadoService.buscarPorPuesto(puesto);
        return ResponseEntity.ok(empleadoDTOS);
    }

    //Obtener todos los Empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarTodosLosEmpleados(){
        List<EmpleadoDTO> empleadoDTOS = empleadoService.buscarTodosLosEmpleados();
        return ResponseEntity.ok(empleadoDTOS);
    }

    @GetMapping("/apellido")
    public ResponseEntity<List<EmpleadoDTO>> buscarEmpleadosPorApellido(@RequestParam String apellido){
        List<EmpleadoDTO> empleadoDTOS = empleadoService.obtenerEmpleadoPorApellido(apellido);
        return ResponseEntity.ok(empleadoDTOS);
    }
}
