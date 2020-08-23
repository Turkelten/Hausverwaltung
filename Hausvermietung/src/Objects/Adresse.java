package Objects;

public class Adresse {
	
	public String ort;
	public String strasse;
	public String hausnummer;
	public String plz;
	
	public Adresse(String ort, String strasse, String hausnummer, String plz) {
		this.ort = ort;
		this.hausnummer = hausnummer;
		this.plz = plz;
		this.strasse = strasse;
	}
	
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
}
