package Jar.mapper;
import Jar.Empleado;
import Jar.dto.EmpleadoResponseDTO;
import Jar.dto.EmpleadoRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {
    @Mapping(
            target = "departamento",
            source = "departamento.nombre"
    )
    @Mapping(
            target = "nombreCompleto",
            expression = "java(empleado.getNombre() + \" \" + empleado.getApellido())"
    )
    EmpleadoResponseDTO toDto(Empleado empleado);
    Empleado toEntity(EmpleadoRequestDTO dto);
}
