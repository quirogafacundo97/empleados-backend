package Jar;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import Jar.dto.EmpleadoDTO;
import Jar.dto.EmpleadoRequestDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import Jar.exception.EmpleadoNoEncontradoException;
import org.springframework.stereotype.Service;

@Service// Le dice a Spring que esta clases es un componente de logica de negocio
public class EmpleadoService {
    //Inyectar el repositorio
    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    //metodo para traer los datos de la BD real
    public List<Empleado> obtenerEmpleados(){
        return empleadoRepository.findAll();
    }

    public EmpleadoDTO obtenerEmpleadoPorId(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));
        return convertirEmpleadoDTO(empleado);
    }

    //recibe nuevo empleado y lo guarda en la base de datos usando el repositorio.
    public EmpleadoDTO guardarEmpleado(EmpleadoRequestDTO requestDTO){
        Empleado empleado=convertirEmpleado(requestDTO);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return(convertirEmpleadoDTO(empleadoGuardado));
    }

    //buscar un empleado viejo por su ID y cambiar algunos de dus datos.
    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoRequestDTO requestDTO){
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
    public List<EmpleadoDTO> buscarEmpleadoPorDepartamento(String nombre){
        departamentoRepository.findByNombre(nombre).orElseThrow(()->new DepartamentoNoEncontradoException(nombre));
        List<Empleado> empleados = empleadoRepository.findByDepartamentoNombre(nombre);
        List<EmpleadoDTO> empleadoDTOs = new ArrayList<>();
        for(Empleado empleado : empleados){
            empleadoDTOs.add(convertirEmpleadoDTO(empleado));
        }
        return empleadoDTOs;
    }

    //Buscar empleados cuyo apellido empiecen con un prefijo
    public List<EmpleadoDTO> apellidoStartingWith(String prefijo){
        List<Empleado> empleados = empleadoRepository.findByApellidoStartingWith(prefijo);
        List<EmpleadoDTO> empleadoDTOs = new ArrayList<>();
        for(Empleado empleado : empleados){
            empleadoDTOs.add(convertirEmpleadoDTO(empleado));
        }
        return empleadoDTOs;
    }

    //Buscar empleados cuyo apellido contengan alguna palabra
    public List<EmpleadoDTO> apellidoContaining (String palabra){
        List<Empleado> empleados = empleadoRepository.findByApellidoContaining(palabra);
        List<EmpleadoDTO> empleadoDTOs = new ArrayList<>();
        for(Empleado empleado : empleados){
            empleadoDTOs.add(convertirEmpleadoDTO(empleado));
        }
        return empleadoDTOs;
    }

    //Buscar empleados por puesto

    public List<EmpleadoDTO> buscarPorPuesto(String puesto){
        List<Empleado> empleados = empleadoRepository.findByPuesto(puesto);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("puesto", puesto);
        }

        List<EmpleadoDTO> empleadoDTOs = new ArrayList<>();

        for (Empleado empleado : empleados) {
            empleadoDTOs.add(convertirEmpleadoDTO(empleado));
        }
        return empleadoDTOs;
    }

    public List<EmpleadoDTO> buscarTodosLosEmpleados(){
        List<Empleado> empleados = empleadoRepository.findAll();
        List<EmpleadoDTO> empleadoDTOs = new ArrayList<>();

        for (Empleado empleado : empleados) {
            empleadoDTOs.add(convertirEmpleadoDTO(empleado));
        }
        return empleadoDTOs;
    }

    //Convierte la Entidad Empleado a un EmpleadoDTO.
    private EmpleadoDTO convertirEmpleadoDTO(Empleado empleado){
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId(empleado.getId());
        empleadoDTO.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        empleadoDTO.setPuesto(empleado.getPuesto());
        if(empleado.getDepartamento()!=null) {
            empleadoDTO.setDepartamento(empleado.getDepartamento().getNombre());
        }
        else{
            empleadoDTO.setDepartamento("Sin departamento");
        }
        return empleadoDTO;
    }

    public List<EmpleadoDTO> obtenerEmpleadoPorApellido(String apellido){
        List<Empleado> empleados = empleadoRepository.findByApellido(apellido);
        if(empleados.isEmpty()){
            throw new EmpleadoNoEncontradoException("apellido", apellido);
        }
        List<EmpleadoDTO> empleadosDTO = new ArrayList<>();

        for(Empleado empleado : empleados){
            empleadosDTO.add(convertirEmpleadoDTO(empleado));
        }
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
