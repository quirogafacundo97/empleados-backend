package Jar;


import java.util.List;
import java.util.ArrayList;

import Jar.dto.EmpleadoResponseDTO;
import Jar.dto.EmpleadoRequestDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import Jar.exception.EmpleadoNoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service// Le dice a Spring que esta clases es un componente de logica de negocio
public class EmpleadoService {
    //Inyectar el repositorio
    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public EmpleadoResponseDTO obtenerEmpleadoPorId(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));
        return convertirEmpleadoDTO(empleado);
    }

    //recibe nuevo empleado y lo guarda en la base de datos usando el repositorio.
    public EmpleadoResponseDTO guardarEmpleado(EmpleadoRequestDTO requestDTO){
        Empleado empleado=convertirEmpleado(requestDTO);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return(convertirEmpleadoDTO(empleadoGuardado));
    }

    //buscar un empleado viejo por su ID y cambiar algunos de dus datos.
    public EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO requestDTO){
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));
        Departamento departamento = departamentoRepository.findById(requestDTO.getDepartamentoId()).orElseThrow(()->new DepartamentoNoEncontradoException(requestDTO.getDepartamentoId()));

        //Actualizo los campos de empleadoExistente
        empleadoExistente.setNombre(requestDTO.getNombre());
        empleadoExistente.setApellido(requestDTO.getApellido());
        empleadoExistente.setPuesto(requestDTO.getPuesto());
        empleadoExistente.setDepartamento(departamento);

        //Guardo los cambios en la base de datos
        Empleado empleadoGuardado = empleadoRepository.save(empleadoExistente);

        //Convierto la entidad Empleado a empleadoDTO

        return convertirEmpleadoDTO(empleadoGuardado);

    }

    //Elimina un empleado segun su id
    public void eliminarEmpleado(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));

        empleadoRepository.delete(empleado);
    }

    //Buscar empleados por departamento
    public Page<EmpleadoResponseDTO> buscarEmpleadoPorDepartamento(String nombre, Pageable pageable){
        departamentoRepository.findByNombre(nombre).orElseThrow(()->new DepartamentoNoEncontradoException(nombre));
        Page<Empleado> empleados = empleadoRepository.findByDepartamentoNombre(nombre, pageable);
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.map(this::convertirEmpleadoDTO);

        return empleadoResponseDTOS;
    }

    //Buscar empleados cuyo apellido empiecen con un prefijo
    public List<EmpleadoResponseDTO> apellidoStartingWith(String prefijo){
        List<Empleado> empleados = empleadoRepository.findByApellidoStartingWith(prefijo);
        List<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.stream().map(this::convertirEmpleadoDTO).toList();

        return empleadoResponseDTOS;
    }

    //Buscar empleados cuyo apellido contengan alguna palabra
    public List<EmpleadoResponseDTO> apellidoContaining (String palabra){
        List<Empleado> empleados = empleadoRepository.findByApellidoContaining(palabra);
        List<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.stream().map(this::convertirEmpleadoDTO).toList();

        return empleadoResponseDTOS;
    }

    //Buscar empleados por puesto

    public List<EmpleadoResponseDTO> buscarPorPuesto(String puesto){
        List<Empleado> empleados = empleadoRepository.findByPuesto(puesto);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("puesto", puesto);
        }

        List<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.stream().map(this::convertirEmpleadoDTO).toList();

        return empleadoResponseDTOS;
    }

    public Page<EmpleadoResponseDTO> buscarTodosLosEmpleados(Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findAll(pageable);
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.map(this::convertirEmpleadoDTO);

        return empleadoResponseDTOS;
    }

    //Convierte la Entidad Empleado a un EmpleadoDTO.
    private EmpleadoResponseDTO convertirEmpleadoDTO(Empleado empleado){
        EmpleadoResponseDTO empleadoResponseDTO = new EmpleadoResponseDTO();
        empleadoResponseDTO.setId(empleado.getId());
        empleadoResponseDTO.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        empleadoResponseDTO.setPuesto(empleado.getPuesto());
        if(empleado.getDepartamento()!=null) {
            empleadoResponseDTO.setDepartamento(empleado.getDepartamento().getNombre());
        }
        else{
            empleadoResponseDTO.setDepartamento("Sin departamento");
        }
        return empleadoResponseDTO;
    }

    public List<EmpleadoResponseDTO> obtenerEmpleadoPorApellido(String apellido){
        List<Empleado> empleados = empleadoRepository.findByApellido(apellido);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("apellido", apellido);
        }
        List<EmpleadoResponseDTO> empleadosDTO = empleados.stream().map(this::convertirEmpleadoDTO).toList();

        return empleadosDTO;
    }

    private Empleado convertirEmpleado(EmpleadoRequestDTO requestDTO){
        Long dptoId = requestDTO.getDepartamentoId();
        Departamento departamento = departamentoRepository.findById(dptoId).orElseThrow(()-> new DepartamentoNoEncontradoException(dptoId));

        Empleado empleado = new Empleado();
        empleado.setNombre(requestDTO.getNombre());
        empleado.setApellido(requestDTO.getApellido());
        empleado.setPuesto(requestDTO.getPuesto());
        empleado.setDepartamento(departamento);

        return empleado;
    }
}
