package Jar;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity//le dice a hibernate que esta clase es una tabla en la BD
@Table(name = "empleados")
public class Empleado {
    //atributos privados para cumplir con el encapsulamiento
    @Id// le dice que este atributo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//la pk sea autoincremental
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String puesto;

    @ManyToOne(optional = false)
    @JoinColumn(name="departamento_id", nullable = false)//nombre de la columna en PostgreSQL
    @JsonBackReference//Define la clave foranea
    private Departamento departamento;

    //constructor vacio, Hibernate lo necesita para poder rellenar los datos cuando los lee de la BD
    public Empleado(){

    }

    //constructor con parametros
    public Empleado(Long id, String nombre, String apellido, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
    }
    //getters y setters (para acceder y modificar los datos)
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
