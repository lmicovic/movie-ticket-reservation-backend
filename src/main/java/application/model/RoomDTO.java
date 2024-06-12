package application.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "room")
public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 709720629673232533L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 25)
	private String name;

	@Column(name = "rows")
	private Integer rows;

	@Column(name = "columns")
	private Integer columns;

	public RoomDTO() {

	}

	public RoomDTO(String name, Integer rows, Integer columns) {
		super();
		this.name = name;
		this.rows = rows;
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public Long getId() {
		return id;
	}
	
	
	public boolean equals(RoomDTO room) {
		
		if(this.getId() == room.getId()) {
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public String toString() {
		return "RoomDTO [id=" + id + ", name=" + name + ", rows=" + rows + ", columns=" + columns + "]";
	}

}
