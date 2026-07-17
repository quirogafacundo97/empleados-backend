package Jar;
import java.util.List;

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
    @GetMapping({"/{id}"})
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id){
        EmpleadoDTO empleadoDTO = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleadoDTO);
    }


    @PostMapping
    public Empleado crearEmpleado(@Valid @RequestBody Empleado nuevoEmpleado){
        return empleadoService.guardarEmpleado(nuevoEmpleado);
    }

    @PutMapping("{id}")
    public Empleado actualizarEmpleadoPorId(@PathVariable Long id,@Valid @RequestBody Empleado empleadoDetalles){
        return empleadoService.actualizarEmpleado(id, empleadoDetalles);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarEmpleadoPorId(@PathVariable Long id){
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //Obtener empleados por puesto usando PathVariable
    @GetMapping("/puesto/{puesto}")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleadosPorPuesto(@PathVariable String puesto){
        List<EmpleadoDTO> empleados = empleadoService.buscarPorPuesto(puesto);
        return ResponseEntity.ok(empleados);
    }
    //Obtener empleados por nombre de departamento
    @GetMapping("/departamento/{nombre}")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleadosPorDepartamento(@PathVariable String nombre){
        List<EmpleadoDTO> empleados = empleadoService.buscarEmpleadoPorDepartamento(nombre);
        return ResponseEntity.ok(empleados);
    }
    //Obtener empleados por un apellido que tenga un determinado prefijo
    @GetMapping("/apellido/prefijo/{prefijo}")
    public ResponseEntity<List<EmpleadoDTO>>  obtenerEmpleadosPorPrefijo(@PathVariable String prefijo){
        List<EmpleadoDTO> empleados = empleadoService.apellidoStartingWith(prefijo);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/apellido/contiene{palabra}")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleadosApellidoContaining(@PathVariable String palabra){
        List<EmpleadoDTO> empleados = empleadoService.apellidoContaining(palabra);
        return ResponseEntity.ok(empleados);
    }

    // Obtener empleados por puesto usando RequestParam
    @GetMapping("/buscar")
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
}
