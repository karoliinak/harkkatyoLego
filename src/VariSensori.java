import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

public class VariSensori implements Runnable {

	private int mustaVari;
	private int valkoinenVari;
	private int viivaVari;
	private int viivaMin = viivaVari - 5;
	private int viivaMax = viivaVari + 5;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	public VariSensori(ColorSensor cSensori, Ajaja ajaja) {
		this.cSensori = cSensori;
		this.ajaja = ajaja;
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
	}

	public void run() {
		// Laitetaan RGB Sensorin punainen valo p��lle
		cSensori.setFloodlight(true);
		
		// Tallennetaan v�rit muuttujaan
		asetaVarit();

		while (!Button.ESCAPE.isDown()) {
			ajaja.liiku();
			
			// T�� ei oo siis toimiva systeemi, mietint� viel� kesken!
			if (cSensori.getLightValue() < viivaVari) {
				ajaja.kaarraOikealle();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ajaja.liiku();
			} 
			if (cSensori.getLightValue() > viivaVari) {
				ajaja.kaarraVasemmalle();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ajaja.liiku();
			} 
		}
	}
}
