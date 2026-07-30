package Jar;
import Jar.exception.DepartamentoNoEncontradoException;
import org.springframework.stereotype.Service;
import Jar.dto.DepartamentoResponseDTO;
import Jar.dto.DepartamentoRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;
    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public DepartamentoResponseDTO guardarDepartamento(DepartamentoRequestDTO requestDTO) {
        Departamento departamento = convertirDepartamento(requestDTO);
        Departamento departamentoGuardado = departamentoRepository.save(departamento);

        return convertirDepartamentoDTO(departamentoGuardado);
    }

    public DepartamentoResponseDTO obtenerDepartamentoPorId(Long id) {
        Departamento departamento=departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        return convertirDepartamentoDTO(departamento);
    }

    public void eliminarDepartamento(Long id) {
        departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        departamentoRepository.deleteById(id);
    }

    public DepartamentoResponseDTO actualizarDepartamento(Long id, DepartamentoRequestDTO requestDTO) {
        Departamento departamentoExistente = departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        departamentoExistente.setNombre(requestDTO.getNombre());
        Departamento departamentoActualizado = departamentoRepository.save(departamentoExistente);
        return  convertirDepartamentoDTO(departamentoActualizado);
    }

    public List<DepartamentoResponseDTO> obtenerDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        List<DepartamentoResponseDTO> responseDTOS = departamentos.stream().map(this::convertirDepartamentoDTO).toList();

        return responseDTOS;
    }

    private Departamento convertirDepartamento(DepartamentoRequestDTO requestDTO){
        Departamento departamento = new Departamento();
        departamento.setNombre(requestDTO.getNombre());
        return departamento;
    }

    private DepartamentoResponseDTO convertirDepartamentoDTO(Departamento departamento){
        DepartamentoResponseDTO dto = new DepartamentoResponseDTO();
        dto.setNombre(departamento.getNombre());
        dto.setId(departamento.getId());
        return dto;
    }

}
