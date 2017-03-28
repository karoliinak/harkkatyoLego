import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Motor;

public class VariSensori implements Runnable {

	private int mustaVari;
	private int valkoinenVari;
	private int viivaVari;
	private int viivaMin;
	private int viivaMax;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	public VariSensori(ColorSensor cSensori, Ajaja ajaja) {
		this.cSensori = cSensori;
		this.ajaja = ajaja;
	}

	public void asetaVarit() {
/*
		// Tallennetaan mustan arvo muuttujaan
		System.out.println("Lue musta");
		Button.waitForAnyPress();
		mustaVari = cSensori.getLightValue();

		// Tallennetaan valkoisen arvo muuttujaan
		System.out.println("Lue valkoinen");
		Button.waitForAnyPress();
		valkoinenVari = cSensori.getLightValue();
*/
		// Tallennetaan viivan arvo muuttujaan
		System.out.println("Lue viiva");
		Button.waitForAnyPress();
		viivaVari = cSensori.getLightValue();
		Button.waitForAnyPress();
		
		viivaMin = viivaVari - 2;
		viivaMax = viivaVari + 2;
		
	}

	public void run() {
		// Laitetaan RGB Sensorin punainen valo p��lle
		cSensori.setFloodlight(true);
		
		// Tallennetaan v�rit muuttujaan
		asetaVarit();

		while (!Button.ESCAPE.isDown()) {
			
			if (cSensori.getLightValue() < viivaMin) {
				ajaja.kaarraOikealle();
	
			} 
			if (cSensori.getLightValue() > viivaMax) {
				ajaja.kaarraVasemmalle();
			}
			if (cSensori.getLightValue() > viivaMin && cSensori.getLightValue() < viivaMax){
				ajaja.liiku();
			}
		}
	}
	/*
	public int palautaVari() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LCD.clear();
		System.out.println(cSensori.getLightValue());
		return cSensori.getLightValue();
	}*/
}
