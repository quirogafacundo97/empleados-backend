package Jar;
import Jar.exception.DepartamentoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;
    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public Departamento guardarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento obtenerDepartamentoPorId(Long id) {
        Departamento departamento=departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));
        return departamento;
    }

    public void eliminarDepartamento(Long id) {
        Departamento departamento = departamentoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el departamento con id: " + id));

        departamentoRepository.deleteById(id);
    }

    public Departamento actualizarDepartamento(Long id, Departamento departamento) {
        Departamento departamentoExistente = departamentoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el departamento con id: " + id));

        departamentoExistente.setNombre(departamento.getNombre());
        return  departamentoRepository.save(departamentoExistente);
    }

    public List<Departamento> obtenerDepartamentos() {
        return departamentoRepository.findAll();
    }

}
