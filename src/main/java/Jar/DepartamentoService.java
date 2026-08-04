package Jar;
import Jar.dto.DepartamentoConEmpleadosDTO;
import Jar.dto.EmpleadoResponseDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import org.springframework.stereotype.Service;
import Jar.dto.DepartamentoResponseDTO;
import Jar.dto.DepartamentoRequestDTO;
import java.util.List;

@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoService empleadoService;

    public DepartamentoService(DepartamentoRepository departamentoRepository, EmpleadoService empleadoService) {
        this.departamentoRepository = departamentoRepository;
        this.empleadoService = empleadoService;
    }

    public DepartamentoResponseDTO guardarDepartamento(DepartamentoRequestDTO requestDTO) {
        Departamento departamento = convertirDepartamento(requestDTO);
        Departamento departamentoGuardado = departamentoRepository.save(departamento);

        return convertirDepartamentoDTO(departamentoGuardado);
    }

    public DepartamentoConEmpleadosDTO obtenerDepartamentoPorId(Long id) {
        Departamento departamento=departamentoRepository.findById(id).orElseThrow(()->new DepartamentoNoEncontradoException(id));

        return convertirDepartamentoConEmpleados(departamento);
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
        return departamentos.stream().map(this::convertirDepartamentoDTO).toList();

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

    private DepartamentoConEmpleadosDTO convertirDepartamentoConEmpleados(Departamento departamento){
        DepartamentoConEmpleadosDTO departamentoConEmpleadosDTO = new DepartamentoConEmpleadosDTO();
        departamentoConEmpleadosDTO.setId(departamento.getId());
        departamentoConEmpleadosDTO.setNombre(departamento.getNombre());
        departamentoConEmpleadosDTO.setEmpleados(convertirListaEmpleadosDTOs(departamento.getEmpleados()));
        return departamentoConEmpleadosDTO;
    }

    private List<EmpleadoResponseDTO> convertirListaEmpleadosDTOs(List<Empleado> empleados){
        return empleados.stream().map(empleadoService::convertirEmpleadoDTO).toList();
    }

}
