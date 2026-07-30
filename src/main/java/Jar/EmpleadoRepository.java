package Jar;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository //Le avisamos a Spring que este componente maneja la BD
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{
    List<Empleado> findByPuesto(String puesto);
    Page<Empleado> findByDepartamentoNombre(String nombre, Pageable pageable);
    List<Empleado> findByApellidoStartingWith(String apellido);
    List<Empleado> findByApellidoContaining(String apellido);
    List<Empleado> findByApellido(String apellido);
}
