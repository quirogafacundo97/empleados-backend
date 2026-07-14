package Jar;

import java.util.List;
import java.util.ArrayList;

import Jar.dto.EmpleadoDTO;
import org.springframework.stereotype.Service;

@Service// Le dice a Spring que esta clases es un componente de logica de negocio
public class EmpleadoService {
    //Inyectar el repositorio
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    //metodo para traer los datos de la BD real
    public List<Empleado> obtenerEmpleados(){
        return empleadoRepository.findAll();
    }

    public EmpleadoDTO obtenerEmpleadoPorId(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el empleado con el id: " + id));
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

    //recibe nuevo empleado y lo guarda en la base de datos usando el repositorio.
    public Empleado guardarEmpleado(Empleado nuevoEmpleado){
        return empleadoRepository.save(nuevoEmpleado);
    }

    //buscar un empleado viejo por su ID y cambiar algunos de dus datos.
    public Empleado actualizarEmpleado(long id, Empleado empleadoDetalles){
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el empleado con id: " + id));

        //Le damos los campos con la nueva informacion
        empleadoExistente.setNombre(empleadoDetalles.getNombre());
        empleadoExistente.setApellido(empleadoDetalles.getApellido());
        empleadoExistente.setPuesto(empleadoDetalles.getPuesto());
        empleadoExistente.setDepartamento(empleadoDetalles.getDepartamento());
        return empleadoRepository.save(empleadoExistente);

    }

    //Elimina un empleado segun su id
    public void eliminarEmpleado(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el empleado con id: " + id));

        empleadoRepository.delete(empleado);
    }

    //Buscar empleados por puesto
    public List<Empleado> buscarEmpleadoPorPuesto(String puesto){
        return empleadoRepository.findByPuesto(puesto);
    }

    //Buscar empleados por departamento
    public List<Empleado> buscarEmpleadoPorDepartamento(String nombre){
        return empleadoRepository.findByDepartamentoNombre(nombre);
    }

    //Buscar empleados cuyo apellido empiecen con un prefijo
    public List<Empleado> apellidoStartingWith(String prefijo){
        return empleadoRepository.findByApellidoStartingWith(prefijo);
    }

    //Buscar empleados cuyo apellido contengan alguna palabra
    public List<Empleado> apellidoContaining (String palabra){
        return empleadoRepository.findByApellidoContaining(palabra);
    }
}
