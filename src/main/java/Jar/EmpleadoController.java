package Jar;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empleados") //definir una ruta base con version

public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> obtenerTodosLosEmpleados(){
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/{id}") //el {id} significa que esa parte de la ruta es variable
    public Empleado obtenerEmpleadoPorId(@PathVariable Long id){
        return empleadoService.obtenerEmpleadoPorId(id);
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
    public void eliminarEmpleadoPorId(@PathVariable Long id){
        empleadoService.eliminarEmpleado(id);
    }

}
