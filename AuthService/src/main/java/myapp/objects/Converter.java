package myapp.objects;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface Converter {
	
	
	public String getString(User u);

}
