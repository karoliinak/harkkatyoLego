import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;

/**
 * Sensorit -luokkaa k�ytet��n viivan seurantaan, edess� olevan esteen havaitsemiseen ja kuluneen ajan tulostamiseen.
 * 
 * @author Karoliina Kunnas
 * @version 1.0
 * @since
 */

public class Sensorit implements Runnable {

	// Alustukset
	private int viivaVari;
	private int viivaMin;
	private int viivaMax;
	private int jyrkkaMin;
	private int jyrkkaMax;

	private Ajastin ajastin;
	private UltrasonicSensor uSensori;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	/**
	 * @param cSensori
	 * @param uSensori
	 * @param ajaja
	 * @param ajastin
	 * 		Sensorit saa Ajoluokka -luokassa luodut ColorSensor, UltrasonicSensor ja Ajaja -oliot.
	 */
	Sensorit(ColorSensor cSensori, UltrasonicSensor uSensori, Ajaja ajaja,
			Ajastin ajastin) {
		this.cSensori = cSensori;
		this.uSensori = uSensori;
		this.ajaja = ajaja;
		this.ajastin = ajastin;
	}
	
	/**
	 * Lukee valoarvon RGB-sensorilla ja tallentaa sen muuttujaan viivaVari. Laskee viivaMin, viivaMax, jyrkkaMin ja
	 * jyrkkaMax arvot k�ytt�en viivaVari arvoa. 
	 */

	// Tallennetaan valoarvot muuttujaan, lasketaan tarvittavat minimi ja
	// maksimi arvot
	public void asetaValoArvot() {
		// Tallennetaan viivan arvo muuttujaan
		LCD.drawString("Lue viivan", 2, 3);
		LCD.drawString("valoarvo", 3, 4);
		Button.waitForAnyPress();
		viivaVari = cSensori.getLightValue();
		Button.waitForAnyPress();
		LCD.clear();

		// Lasketaan kaartamista varten minimi ja maksimi arvot muuttujiin
		viivaMin = viivaVari - 2;
		viivaMax = viivaVari + 2;
		jyrkkaMin = viivaVari - 15;
		jyrkkaMax = viivaVari + 15;
	}

	/**
	 *  Asettaa robotin RGB-sensorin valon p��lle ja tallentaa viivan valoarvon muuttujaan, jonka j�lkeen
	 *  asettaa kumman puolen seuraaja robotti on. Sirtyy vaiheeseen 1 ja kutsuu ajastimen aloitusaika() -metodia.
	 *  
	 *  Tutkii vaiheen 1 aikana valoarvoa RGB-sensorilla ja samalla tutkii ultra��ni-sensorilla onko 
	 *  edess� estett�. Vasemman puolen seuraaja: kaartaa loivasti oikealle, jos viivan valoarvo on pienempi kuin 
	 *  viivaMin ja suurempi kuin jyrkkaMin. kaartaa jyrk�sti oikealle, jos viivan arvo on pienempi kuin jyrkkaMin. 
	 *  Kaartaa loivasti vasemmalle, jos viivan arvo on suurempi kuin viivaMax ja pienempi kuin jyrkkaMax. 
	 *  Kaartaa jyrk�sti vasemmalle, jos viivan arvo on suurempi kuin jyrkkaMax. Liikkuu suoraan eteenp�in, jos 
	 *  viivan arvo on pienempi kuin viivaMax ja suurempi kuin viivaMin. Siirtyy vaiheeseen 2, jos robotti havaitsee 
	 *  esteen 20cm p��st�. 
	 *  
	 *  Kutsuu ajaja-luokan vaistoVasemmalle()- tai vaistoOikealle() -metodia vaiheen 2 aikana riippuen siit�
	 *  kumman puolen seuraaja robotti on. V�ist�n j�lkeen robotti siirtyy vaiheeseen 3. 
	 *  
	 *  Liikkuu suoraan eteenp�in vaiheen 3 aikana niin kauan kun RGB-sensorilla luettu valoarvo on suurempi
	 *  kuin viivaMax. Siirtyy takaisin vaiheeseen 1, kun valoarvo on yht� suuri kuin viivaMax. 
	 *  
	 *  Pys�htyy vaiheen 0 aikana. 
	 *  
	 *  Laskee kuluneen ajan ja tulostaa sen n�yt�lle lopuksi. 
	 */
	
	public void run() {
		// Laitetaan RGB Sensorin punainen valo p��lle
		cSensori.setFloodlight(true);

		// Tallennetaan valoarvot muuttujaan
		asetaValoArvot();

		// Asetetaan kumman puolen seuraaja
		// Puoli 1 == vasemman puolen seuraaja ja puoli 2 == oikean puolen
		ajaja.setPuoli(1);

		// Asetetaan vaihteenksi 1, jolloin VariSensorin if-lauseet on k�yt�ss�
		ajaja.setVaihe(1);

		// Aloitetaan ajanotto
		ajastin.aloitusaika();

		while (!Button.ESCAPE.isDown()) {

			// Robotti seuraa viivaa ja tutkii ultra��nell� onko edess� estett�
			while (ajaja.getVaihe() == 1) {

				// Vasemman puolen viivan seuraajan kaartamiset
				if (cSensori.getLightValue() > viivaMax
						&& cSensori.getLightValue() <= jyrkkaMax
						&& ajaja.getPuoli() == 1) {
					ajaja.kaarraOikealle();
				}
				if (cSensori.getLightValue() < viivaMin
						&& cSensori.getLightValue() >= jyrkkaMin
						&& ajaja.getPuoli() == 1) {
					ajaja.kaarraVasemmalle();
				}
				if (cSensori.getLightValue() > jyrkkaMax
						&& ajaja.getPuoli() == 1) {
					ajaja.jyrkastiOikealle();
				}
				if (cSensori.getLightValue() < jyrkkaMin
						&& ajaja.getPuoli() == 1) {
					ajaja.jyrkastiVasemmalle();
				}

				// Oikean puolen viivan seuraajan kaartamiset
				if (cSensori.getLightValue() < viivaMin
						&& cSensori.getLightValue() >= jyrkkaMin
						&& ajaja.getPuoli() == 2) {
					ajaja.kaarraOikealle();
				}
				if (cSensori.getLightValue() > viivaMax
						&& cSensori.getLightValue() <= jyrkkaMax
						&& ajaja.getPuoli() == 2) {
					ajaja.kaarraVasemmalle();
				}
				if (cSensori.getLightValue() > jyrkkaMax
						&& ajaja.getPuoli() == 2) {
					ajaja.jyrkastiVasemmalle();
				}
				if (cSensori.getLightValue() < jyrkkaMin
						&& ajaja.getPuoli() == 2) {
					ajaja.jyrkastiOikealle();
				}

				// Jos valoarvo viivaMin ja viivaMax arvojen v�liss�, robotti
				// liikkuu suoraan eteenp�in
				if (cSensori.getLightValue() > viivaMin
						&& cSensori.getLightValue() < viivaMax) {
					ajaja.liiku();
				}

				// Jos havaitaan este, siirryt��n vaiheeseen 2
				if (uSensori.getDistance() < 20) {
					ajaja.setVaihe(2);
				}
			}

			// Robotti v�ist�� estett�
			if (ajaja.getVaihe() == 2) {

				// Jos robotti on vasemman puolen seuraaja, v�istet��n
				// vasemmalta
				if (ajaja.getPuoli() == 1) {
					ajaja.vaistoVasemmalle();

					// V�ist�n j�lkeen, siirryt��n vaiheeseen 3
					ajaja.setVaihe(3);
				}

				// Jos robotti on oikean puolen seuraaja, v�istet��n oikealta
				if (ajaja.getPuoli() == 2) {
					ajaja.vaistoOikealle();

					// V�ist�n j�lkeen, siirryt��n vaiheeseen 3
					ajaja.setVaihe(3);
				}
			}

			// Palataan takaisin viivalle v�ist�n j�lkeen
			if (ajaja.getVaihe() == 3) {

				// Kun valoarvo on valkoisella eli yli viivaMax arvon, robotti
				// liikkuu suoraan eteenp�in
				while (cSensori.getLightValue() > viivaMax) {
					ajaja.liiku();
				}

				// Viivalle palaamisen j�lkeen, siirryt��n takaisin vaiheeseen 1
				ajaja.setVaihe(1);
			}

			// Robotti pys�htyy
			if (ajaja.getVaihe() == 0) {
				ajaja.pysahdy();

				// Lopetetaan ajanotto
				ajastin.lopetusaika();

				// Lasketaan kulunut aika
				LCD.drawString("Aikaa kului", 2, 3);
				LCD.drawString(String.valueOf(ajastin.kulunutaika()), 5, 4);
			}

		}
	}

}
