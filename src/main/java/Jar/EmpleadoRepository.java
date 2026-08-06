package Jar;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository //Le avisamos a Spring que este componente maneja la BD
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{
    Page<Empleado> findByPuesto(String puesto, Pageable pageable);
    Page<Empleado> findByDepartamentoNombre(String nombre, Pageable pageable);
    Page<Empleado> findByApellidoStartingWith(String apellido, Pageable pageable);
    Page<Empleado> findByApellidoContaining(String apellido, Pageable pageable);
    Page<Empleado> findByApellido(String apellido, Pageable pageable);
}
