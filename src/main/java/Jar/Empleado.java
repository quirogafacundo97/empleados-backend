package Jar;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity//le dice a hibernate que esta clase es una tabla en la BD
@Table(name = "empleados")
public class Empleado {
    //atributos privados para cumplir con el encapsulamiento
    @Id// le dice que este atributo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//la pk sea autoincremental
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, message = "El nombre debe tener a menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @NotBlank(message = "El puesto no puede estar vacio")
    private String puesto;

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



    @ManyToOne
    @JoinColumn(name="departamento_id")//nombre de la columna en PostgreSQL
    @JsonBackReference//Define la clave foranea
    private Departamento departamento;
    public Departamento getDepartamento() {
        return this.departamento;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
