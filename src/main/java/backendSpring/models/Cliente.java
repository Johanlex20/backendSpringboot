package backendSpring.models;

import jakarta.persistence.*;
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

    @Column
    private String nombre;
    private String apellido;
    private String telefono;


    private String email;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

}
