package ch.bfh.ti.sed.pm.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class Measurement keeps track of the temperature of the patient and the exact date and time that this one was taken.
 */
@Entity
public class Measurement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double temperature;

	@Column(name = "date")
	private LocalDateTime date;

	// TODO: Javadoc missing
	public Measurement() {}

	public Measurement(double temperature) {
		this.temperature = temperature;
		this.date = LocalDateTime.now();
	}

	/* Getter Methods */

	public double getTemperature() {
		return temperature;
	}

	public String getDate() {

		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy '@' hh:mm:ss a");

		String zdtString = FORMATTER.format(this.date);

		return zdtString;
	}

	@Override
	public String toString() {
		return "Measurement: " +
			   " |\tid: " + id +
			   " |\ttemperature: " + String.format("%.1f", temperature) +
			   " |\tdate: " + this.getDate() + '\n';
	}
}
