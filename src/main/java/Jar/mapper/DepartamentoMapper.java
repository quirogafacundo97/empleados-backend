package Jar.mapper;
import Jar.Departamento;
import Jar.dto.DepartamentoConEmpleadosDTO;
import Jar.dto.DepartamentoResponseDTO;
import Jar.dto.DepartamentoRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EmpleadoMapper.class)
public interface DepartamentoMapper {
    DepartamentoConEmpleadosDTO toConEmpleadosDto(Departamento departamento);
    DepartamentoResponseDTO toResponseDto(Departamento departamento);
    Departamento toEntity(DepartamentoRequestDTO dto);

}
