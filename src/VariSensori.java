import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

public class VariSensori {

	private int mustaVari;
	private int valkoinenVari;
	private int viivaVari;
	private int viivaMax = viivaVari + 5;
	private int viivaMin = viivaVari - 5;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	// Luodaan ColorSensor olio
	// ColorSensor sensori = new ColorSensor(SensorPort.S4);

	public VariSensori(ColorSensor cSensori, Ajaja ajaja) {
		this.cSensori = cSensori;
		this.ajaja = ajaja;
		// Laitetaan RGB Sensorin punainen valo p��lle rakentajassa
		cSensori.setFloodlight(true);
	}

	public void asetaVarit() {

		// Tallennetaan mustan arvo muuttujaan
		System.out.println("Lue musta");
		Button.waitForAnyPress();
		mustaVari = cSensori.getLightValue();
		Button.waitForAnyPress();

		// Tallennetaan valkoisen arvo muuttujaan
		System.out.println("Lue valkoinen");
		Button.waitForAnyPress();
		valkoinenVari = cSensori.getLightValue();
		Button.waitForAnyPress();

		// Tallennetaan viivan arvo muuttujaan
		System.out.println("Lue viiva");
		Button.waitForAnyPress();
		viivaVari = cSensori.getLightValue();
		Button.waitForAnyPress();

		// Tulostetaan n�yt�lle arvot
		System.out.println("Musta on " + mustaVari + ", valkoinen on "
				+ valkoinenVari + " ja viiva on " + viivaVari);
		Button.waitForAnyPress();
	}

	public void run() {
		while (!Button.ESCAPE.isDown()) {
			// Jos pystytte jotenkin testaamaan t�t� niin ois kiva
			// Ideana mulla oli ett� se robotin liikkumisalue ois viivan v�rin arvo +- 5, eli se ei ihan heti pienien 
			// v�ri muutosten my�t� l�htis kaartamaan mihink��n
			if (cSensori.getLightValue() < viivaMin) {
				ajaja.kaarraOikealle();
			} else if (cSensori.getLightValue() > viivaMax) {
				ajaja.kaarraVasemmalle();
			} else {
				ajaja.liiku();
			}
			
			// Noita mustan ja valkosen arvoja voisi varmaan hy�dynt�� jyrkiss� k��nn�ksiss�, 
			// siis jos robotti hukkaa sen viivan reunan eli on selkeesti mustalla nii se k��ntyy jyrkemmin 
			// oikealle kuin mit� se tekee tossa ylemp�n�? 
		}
	}
}
