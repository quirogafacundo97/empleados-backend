package Jar;

import java.util.List;
import java.util.ArrayList;

import Jar.dto.EmpleadoDTO;
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
    public Empleado guardarEmpleado(Empleado nuevoEmpleado){
        return empleadoRepository.save(nuevoEmpleado);
    }

    //buscar un empleado viejo por su ID y cambiar algunos de dus datos.
    public Empleado actualizarEmpleado(long id, Empleado empleadoDetalles){
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));

        //Le damos los campos con la nueva informacion
        empleadoExistente.setNombre(empleadoDetalles.getNombre());
        empleadoExistente.setApellido(empleadoDetalles.getApellido());
        empleadoExistente.setPuesto(empleadoDetalles.getPuesto());
        empleadoExistente.setDepartamento(empleadoDetalles.getDepartamento());
        return empleadoRepository.save(empleadoExistente);

    }

    //Elimina un empleado segun su id
    public void eliminarEmpleado(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new EmpleadoNoEncontradoException(id));

        empleadoRepository.delete(empleado);
    }

    //Buscar empleados por puesto
    public List<Empleado> buscarEmpleadoPorPuesto(String puesto){
        return empleadoRepository.findByPuesto(puesto);
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

    //Converite la Entidad Empleado a un EmpleadoDTO.
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
}
