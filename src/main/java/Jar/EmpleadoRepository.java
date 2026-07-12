package Jar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Le avisamos a Spring que este componente maneja la BD
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{
    //queda vacio
}
