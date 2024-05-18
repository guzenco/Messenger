package myapp.objects;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@jakarta.persistence.Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Entity {
	
	@Column(name = "id")
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	public Entity() {
	}

	public int getId() {
		if(id == null)
			return -1;
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Entity)) {
			return false;
		}
		Entity other = (Entity) obj;
		return Objects.equals(id, other.id);
	}	
	
}
