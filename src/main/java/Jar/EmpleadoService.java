package Jar;


import Jar.dto.EmpleadoResponseDTO;
import Jar.dto.EmpleadoRequestDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import Jar.exception.EmpleadoNoEncontradoException;
import Jar.mapper.EmpleadoMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service// Le dice a Spring que esta clases es un componente de logica de negocio
public class EmpleadoService {
    //Inyectar el repositorio
    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoService(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
        this.empleadoMapper = empleadoMapper;
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
    public Page<EmpleadoResponseDTO> apellidoStartingWith(String prefijo, Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findByApellidoStartingWith(prefijo, pageable);
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.map(this::convertirEmpleadoDTO);
        if(empleadoResponseDTOS.isEmpty()){
            throw new EmpleadoNoEncontradoException("apellido que comience con", prefijo);
        }

        return empleadoResponseDTOS;
    }

    //Buscar empleados cuyo apellido contengan alguna palabra
    public Page<EmpleadoResponseDTO> apellidoContaining (String palabra, Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findByApellidoContaining(palabra,pageable);
        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.map(this::convertirEmpleadoDTO);
        if(empleadoResponseDTOS.isEmpty()){
            throw new EmpleadoNoEncontradoException("apellido que contenga", palabra);
        }
        return empleadoResponseDTOS;
    }

    //Buscar empleados por puesto

    public Page<EmpleadoResponseDTO> buscarPorPuesto(String puesto, Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findByPuesto(puesto, pageable);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("puesto", puesto);
        }

        Page<EmpleadoResponseDTO> empleadoResponseDTOS = empleados.map(this::convertirEmpleadoDTO);

        return empleadoResponseDTOS;
    }

    public Page<EmpleadoResponseDTO> buscarTodosLosEmpleados(Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findAll(pageable);
        return empleados.map(empleadoMapper::toDto);
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

    public Page<EmpleadoResponseDTO> obtenerEmpleadoPorApellido(String apellido, Pageable pageable){
        Page<Empleado> empleados = empleadoRepository.findByApellido(apellido, pageable);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("apellido", apellido);
        }
        Page<EmpleadoResponseDTO> empleadosDTO = empleados.map(this::convertirEmpleadoDTO);

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
