package backendSpring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacio")
    @Column(nullable = false)
    @Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12 caracteres")
    private String apellido;

    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 10, max = 13, message = "el tamaño debe tener 10 numeros")
    @Column(nullable = false)
    private String telefono;

    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una direccion de correo con un formato valido")
    @Column(nullable = false , unique = true)
    private String email;


    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

}
