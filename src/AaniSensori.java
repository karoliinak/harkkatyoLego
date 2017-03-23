import lejos.nxt.UltrasonicSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class AaniSensori implements Runnable {

	private UltrasonicSensor uSensori;
	
	AaniSensori(UltrasonicSensor uSensori) {
		this.uSensori = uSensori;
	}
	
	
	// k�ynnistyy s�ikeen alussa
	public void run() {
		while (!Button.ESCAPE.isDown()) {
			palautaEtaisyys();
		}
	}

	// tulostaa et�isyyden l�himp��n esteeseen robotin n�yt�lle, sek� palauttaa
	// saman arvon
	public synchronized int palautaEtaisyys() {

		LCD.clear();
		System.out.println(uSensori.getDistance());
		return uSensori.getDistance();
	}

}
