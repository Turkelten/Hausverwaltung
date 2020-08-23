package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inserat
{
    public int id;
    public String titel;
    public Haus haus;

    public Inserat(int id, String titel, String beschreibung, float groesse, float zimmer, Boolean einbaukueche, float kaltmiete, float nebenkosten, String hausnummer, String plz, String ort, String strasse)
    {
        this.id = id;
        this.titel = titel;
        this.haus = new Haus(beschreibung, groesse, zimmer, einbaukueche, kaltmiete, nebenkosten, hausnummer, plz, ort, strasse);
        
    }

    public Inserat(ResultSet result) throws SQLException
    {
        this.id = result.getInt("id");
        this.titel = result.getString("titel");
        this.haus = new Haus(result.getString("beschreibung"), result.getFloat("groesse"), result.getFloat("zimmer"), result.getBoolean("einbaukueche"),
        		result.getFloat("kaltmiete"), result.getFloat("nebenkosten"), result.getString("hausnummer"), result.getString("plz"), result.getString("ort"),
        		result.getString("strasse"));
        
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Haus getHaus() {
		return haus;
	}

	public void setHaus(Haus haus) {
		this.haus = haus;
	}

    
    
}
