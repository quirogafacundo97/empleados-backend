package Jar;
import Jar.dto.DepartamentoConEmpleadosDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import Jar.mapper.DepartamentoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import Jar.dto.DepartamentoResponseDTO;
import Jar.dto.DepartamentoRequestDTO;


@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoMapper departamentoMapper;

    public DepartamentoService(DepartamentoRepository departamentoRepository, EmpleadoService empleadoService, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    public DepartamentoResponseDTO guardarDepartamento(DepartamentoRequestDTO requestDTO) {
        Departamento departamentoGuardado = departamentoRepository.save(departamentoMapper.toEntity(requestDTO));

        return departamentoMapper.toResponseDto(departamentoGuardado);
    }

    public DepartamentoConEmpleadosDTO obtenerDepartamentoPorId(Long id) {
        Departamento departamento=departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        return departamentoMapper.toConEmpleadosDto(departamento);
    }

    public void eliminarDepartamento(Long id) {
        departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        departamentoRepository.deleteById(id);
    }

    public DepartamentoResponseDTO actualizarDepartamento(Long id, DepartamentoRequestDTO requestDTO) {
        Departamento departamentoExistente = departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        departamentoExistente.setNombre(requestDTO.getNombre());
        Departamento departamentoActualizado = departamentoRepository.save(departamentoExistente);
        return  departamentoMapper.toResponseDto(departamentoActualizado);
    }

    public Page<DepartamentoResponseDTO> obtenerDepartamentos(Pageable pageable) {
        Page<Departamento> departamentos = departamentoRepository.findAll(pageable);
        return departamentos.map(departamentoMapper::toResponseDto);

    }

}
