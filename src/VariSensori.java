import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

/** 
 * <h1>VariSensori</h1>
 * VariSensori -luokkassa k�ytet��n Lego NXT 2.0 -robotin RGB-v�risensoria valoarvojen lukemiseen. 
 * Valoarvojen avulla robotti seuraa viivaa radalla. 
 * 
 * Luokassa on kaksi metodia:
 * asetaVarit()
 * 		Luetaan radalla olevan viivan valoarvo RGB-sensorilla ja tallennetaan se muuttujaan viivaVari. 
 *		Muuttujaa viivaVari k�ytet��n viivaMax ja viivaMin muuttujien arvojen laskemiseen. Muuttuja viivaMax on seurattavan
 *		viivan maksimi valoarvo ja muuttuja viivaMin on seurattavan viivan minimi valoarvo. 
 * run()
 *		Asettaa RGB -sensorin valon p��lle, jonka j�lkeen kutsuu asetaVarit() -metodia. While -loopissa luetaan valoarvoa 
 *		sensorilla ja tutkitaan if -lauseilla onko valoarvo tummempi kuin viivaMin, vaaleampi kuin viivaMax vai onko arvo
 *		tummempi kuin viivaMin ja vaaleampi kuin viivaMax. Jos arvo on tummempi kuin viivaMin, kutsutaan ajaja -luokan 
 *		kaarraOikealle() -metodia ja robotti kaartaa hieman oikealle. Jos arvo on vaaleampi kuin viivaMax, kutsutaan 
 *		ajaja luokan kaarraVasemmalle() -metodia ja robotti kaartaa hieman vasemmalle. Jos arvo on tummempi kuin viivaMin 
 *		ja vaaleampi kuin viivaMax kutsutaan ajaja luokan liiku() -metodia ja robotti liikkuu suoraan eteenp�in. 
 * 
 * @author karoliina1506
 *
 */

public class VariSensori implements Runnable {
	private int viivaVari;
	private int viivaMin;
	private int viivaMax;
	private int jyrkkaMin;
	private int jyrkkaMax;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	public VariSensori(ColorSensor cSensori, Ajaja ajaja) {
		this.cSensori = cSensori;
		this.ajaja = ajaja;
	}

	public void asetaVarit() {
		// Tallennetaan viivan arvo muuttujaan
		System.out.println("Lue viiva");
		Button.waitForAnyPress();
		viivaVari = cSensori.getLightValue();
		Button.waitForAnyPress();
		
		viivaMin = viivaVari - 2;
		viivaMax = viivaVari + 2;
		jyrkkaMin = viivaVari - 10;
		jyrkkaMax = viivaVari + 10;
	}

	public void run() {
		// Laitetaan RGB Sensorin punainen valo p��lle
		cSensori.setFloodlight(true);
		
		// Tallennetaan v�rit muuttujaan
		asetaVarit();
		
		// Asetetaan kumman puolen seuraaja
		ajaja.setPuoli(1);
		// Asetetaan vaihteenksi 1, jolloin VariSensorin if-lauseet on k�yt�ss�
		ajaja.setVaihde(1);
		
		// Puoli 1 == vasemman puolen seuraaja ja puoli 2 == oikean puolen seuraaja
		while (ajaja.getVaihde() == 1) {	
			
			// Vasemman puolen viivan seuraajan kaartamiset
			if (cSensori.getLightValue() > viivaMax && cSensori.getLightValue() <= jyrkkaMax && ajaja.getPuoli() == 1) {
				ajaja.kaarraOikealle();
			} 
			if (cSensori.getLightValue() < viivaMin && ajaja.getPuoli() == 1) {
				ajaja.kaarraVasemmalle();
			}
			if (cSensori.getLightValue() > jyrkkaMax && ajaja.getPuoli() == 1) {
				ajaja.jyrkastiOikealle();
			}
			
			// Oikean puolen viivan seuraajan kaartamiset
			if (cSensori.getLightValue() < viivaMin && ajaja.getPuoli() == 2) {
				ajaja.kaarraOikealle();
			}
			if (cSensori.getLightValue() > viivaMax && cSensori.getLightValue() <= jyrkkaMax && ajaja.getPuoli() == 2) {
				ajaja.kaarraVasemmalle();
			}
			if (cSensori.getLightValue() > jyrkkaMax && ajaja.getPuoli() == 2) {
				ajaja.jyrkastiVasemmalle();
			}
		
			// Jos valoarvo viivaMin ja viivaMax arvojen v�liss�, robotti liikkuu suoraan eteenp�in
			if (cSensori.getLightValue() > viivaMin && cSensori.getLightValue() < viivaMax){
				ajaja.liiku();				
			}
		}
		
		if (ajaja.getVaihde() == 3){
			while (cSensori.getLightValue() > viivaMax){
				ajaja.liiku();
			}
			ajaja.setVaihde(1);
		}
		
		// Jos vaihde on 0, robotti pys�htyy
		if (ajaja.getVaihde() == 0){
			ajaja.pysahdy();
		}
	}

}
