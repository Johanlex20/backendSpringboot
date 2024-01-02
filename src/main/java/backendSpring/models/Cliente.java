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

    @NotEmpty
    @Size(min = 4, max = 15)
    @Column(nullable = false)
    private String nombre;

    @NotEmpty
    @Size(min = 4, max = 15)
    @Column(nullable = false)
    private String apellido;

    @NotEmpty
    @Size(min = 10,max = 13)
    @Column(nullable = false)
    private String telefono;

    @NotEmpty
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

}
