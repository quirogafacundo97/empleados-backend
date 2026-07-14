package Jar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //Le avisamos a Spring que este componente maneja la BD
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{
     public List<Empleado> findByPuesto(String puesto);
     public List<Empleado> findByDepartamentoNombre(String nombre);
     public List<Empleado> findByApellidoStartingWith(String apellido);
     public List<Empleado> findByApellidoContaining(String apellido);

}
