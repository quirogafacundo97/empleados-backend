package Jar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    //Relacion inversa, para ver empleados desde el departamento.
    //"departamento hace referencia al nombre de la variable en la clase Empleado
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Empleado> empleados;

    public Departamento() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Empleado> getEmpleados(){
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados){
        this.empleados = empleados;
    }
}
