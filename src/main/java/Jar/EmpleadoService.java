package Jar;

import java.util.List;
import java.util.ArrayList;
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

    public Empleado obtenerEmpleadoPorId(Long id){
        return empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el empleado con el id: " + id));
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

    public void eliminarEmpleado(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe el empleado con id: " + id));

        empleadoRepository.delete(empleado);
    }

}
