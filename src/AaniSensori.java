import lejos.nxt.UltrasonicSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class AaniSensori implements Runnable {

	UltrasonicSensor sensori = new UltrasonicSensor(SensorPort.S1);

	//k�ynnistyy s�ikeen alussa
	public void run() {
		while (!Button.ESCAPE.isDown()) {
			palautaEtaisyys();
		}
	}

	//tulostaa et�isyyden l�himp��n esteeseen robotin n�yt�lle, sek� palauttaa saman arvon
	public synchronized int palautaEtaisyys() {

		LCD.clear();
		System.out.println(sensori.getDistance());
		return sensori.getDistance();
	}

}
