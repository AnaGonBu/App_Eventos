package eventos.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the eventos database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="eventos")
public class Evento implements Serializable {
	
	

	private static final long serialVersionUID = 1L;
	
	public enum destacado
	{
	    S, N
	}
	public enum estado
	{
	    ACEPTADO, CANCELADO, TERMINADO
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_EVENTO")
	private int idEvento;

	@Column(name="AFORO_MAXIMO")
	private int aforoMaximo;

	private String descripcion;

	private String destacado;

	private String direccion;

	private int duracion;

	private String estado;
	


	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	@Column(name="MINIMO_ASISTENCIA")
	private int minimoAsistencia;

	private String nombre;

	private BigDecimal precio;

	//uni-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="ID_TIPO")
	private Tipo tipo;
	
	

		
	}




