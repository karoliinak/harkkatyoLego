import lejos.nxt.UltrasonicSensor;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class AaniSensori implements Runnable {

	private UltrasonicSensor uSensori;
	private Ajaja ajaja;

	AaniSensori(UltrasonicSensor uSensori, Ajaja ajaja) {
		this.uSensori = uSensori;
		this.ajaja = ajaja;
	}

	// K�ynnistyy s�ikeen alussa
	public void run() {
		while (!Button.ESCAPE.isDown()) {
		
			if (uSensori.getDistance() < 20) {
				ajaja.pysahdy();
				ajaja.liikkeessa = false;
			}	
		}
	}

	// Tulostaa et�isyyden l�himp��n esteeseen robotin n�yt�lle, sek� palauttaa
	// saman arvon
	/*public int palautaEtaisyys() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LCD.clear();
		System.out.println(uSensori.getDistance());
		return uSensori.getDistance();*/
	}


