package org.foi.uzdiz.azugaj.podaci;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.azugaj.klase.Tvrtka;
import org.foi.uzdiz.azugaj.klase.VirtualnoVrijeme;

public class Vozilo {

  public String Registracija;
  public String Opis;
  public float KapacitetKG;
  public float KapacitetM3;
  public int Redoslijed;
  public boolean Dostupan;
  public float zauzetKG;
  public float zauzetM3;
  public float novac;
  public List<Posiljka> posiljke = new ArrayList();

  public Vozilo(String registracija, String opis, float kapacitetKG, float kapacitetM3,
      int redoslijed) {

    Registracija = registracija;
    Opis = opis;
    KapacitetKG = kapacitetKG;
    KapacitetM3 = kapacitetM3;
    Redoslijed = redoslijed;
    Dostupan = true;
    zauzetKG = 0;
    zauzetM3 = 0;
    novac = 0;
  }

  public int getRedoslijed() {
    return Redoslijed;
  }

  public List<Posiljka> posiljkeZaDostavu() {
    return posiljke;
  }

  public void provjeraDostave() {
    if (Dostupan) {
      boolean kretanje = false;
      for (Posiljka posiljka : posiljke) {
        if (posiljka.uslugaDostave.equals("H")) {
          kretanje = true;
          break;
        }
      }
      if ((zauzetKG / KapacitetKG >= 0.5) || (zauzetM3 / KapacitetM3 >= 0.5))
        kretanje = true;
      if (kretanje) {
        zapocniDostavu();
        Dostupan = false;
      }
    }
  }

  public void zapocniDostavu() {
    int vrijemeIsporuke = Integer.parseInt(Tvrtka.getInstance().vrijemeIsporuke);

    LocalDateTime vrijeme = VirtualnoVrijeme.getInstance().dohvatiVrijeme();
    System.out.print("\nVozilo: " + Registracija + " krenulo u "
        + VirtualnoVrijeme.getInstance().dohvatiStringVrijeme());

    int br = 1;
    for (Posiljka posiljka : posiljke) {
      posiljka.vrijemePreuzimanja = vrijeme.plusMinutes(vrijemeIsporuke * br);
      br++;
    }
  }

  public void dostavi() {

    if (!Dostupan) {
      LocalDateTime vrijeme = VirtualnoVrijeme.getInstance().dohvatiVrijeme();
      Tvrtka tvrtka = Tvrtka.getInstance();

      List<Posiljka> zaDostavu = new ArrayList();
      zaDostavu.addAll(posiljke);
      for (Posiljka posiljka : zaDostavu) {
        if (posiljka.vrijemePreuzimanja.isBefore(vrijeme)) {
          System.out.print("\nVozilo: " + Registracija + " dostavilo " + posiljka.oznaka + " u "
              + VirtualnoVrijeme.getInstance().dohvatiStringVrijeme());

          if (posiljka.iznosPouzeca != 0)
            novac += posiljka.iznosPouzeca;

          tvrtka.DostavljenePosiljke.add(posiljka);
          posiljke.remove(posiljka);
        }
      }
      if (posiljke.isEmpty()) {
        Dostupan = true;
        zauzetKG = 0;
        zauzetM3 = 0;
        novac = 0;
        return;
      }
    }

  }

  public boolean utovari(Posiljka posiljka) {

    if (!Dostupan) {
      return false;
    }
    float volumen = posiljka.visina * posiljka.sirina * posiljka.duzina;
    if (((zauzetKG + posiljka.tezina) > KapacitetKG) || ((zauzetM3 + volumen) > KapacitetM3)) {
      return false;
    }
    Tvrtka tvrtka = Tvrtka.getInstance();
    posiljke.add(posiljka);
    tvrtka.ZaprimljenePosiljke.remove(posiljka);
    return true;
  }



}
