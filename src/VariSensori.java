import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

public class VariSensori implements Runnable {

	private int mustaVari;
	private int valkoinenVari;
	private int viivaVari;
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

			// T�� ei oo siis toimiva systeemi, mietint� viel� kesken!
			if (cSensori.getLightValue() < viivaVari) {
				ajaja.kaarraOikealle();
			} else if (cSensori.getLightValue() > viivaVari) {
				ajaja.kaarraVasemmalle();
			} else {
				ajaja.liiku();
			}

		}
	}
}
