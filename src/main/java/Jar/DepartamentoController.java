package Jar;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    public Departamento crearDepartamento(@Valid @RequestBody Departamento departamento) {
        return departamentoService.guardarDepartamento(departamento);
    }

    @GetMapping("/{id}")
    public Departamento obtenerDepartamento(@PathVariable Long id) {
        return departamentoService.obtenerDepartamentoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarDepartamento(@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
    }

    @PutMapping("/{id}")
    public Departamento actualizarDepartamento(@Valid @RequestBody Departamento departamento, @PathVariable Long id) {
        return departamentoService.actualizarDepartamento(id, departamento);
    }

    @GetMapping
    public List<Departamento> obtenerDepartamentos() {
        return departamentoService.obtenerDepartamentos();
    }




}
