package com.aladdinworks8.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="capacity_sensors")
@Getter @Setter @NoArgsConstructor
public class CapacitySensor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="capacity_sensor_id")
	private Integer capacitySensorId;
    
  	@Column(name="capacity")
	private double capacity;
    
	




}
