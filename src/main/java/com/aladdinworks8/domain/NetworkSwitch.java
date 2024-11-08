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
@Table(name="network_switchs")
@Getter @Setter @NoArgsConstructor
public class NetworkSwitch {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="network_switch_id")
	private Integer networkSwitchId;
    
  	@Column(name="brand")
	private String brand;
    
  	@Column(name="model")
	private String model;
    
	




}
