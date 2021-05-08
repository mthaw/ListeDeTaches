//Cette classe facilite le marshall et unmarshall des fichiers contenant des objets LocalDate. Elle aide a convertir les objets LocalDate en representation XML (String) et vice-versa, qui sera autrement impossible
package application;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	/**
	 * Faire le unmarshal d'une date sous forme de String; le transformer en representation JavaFX: objet LocalDate
	 * @param v c'est la date sous forme de string a transformer
	 */
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	/**
	 * Faire le marshal d'une date sous forme de LocalDate; le transformer en representation XML: String
	 * @param v c'est la date sous forme de LocalDate a transformer
	 */
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}
