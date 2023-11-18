package org.foi.uzdiz.azugaj.datoteke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.azugaj.podaci.Vozilo;

public class DatotekaVozila extends Datoteka {

  List<Vozilo> listaVozila;
  int brojpogresaka = 0;

  public DatotekaVozila(List<String[]> sadrzaj) {

    List<Vozilo> lista = new ArrayList();

    for (String[] el : sadrzaj) {
      try {
        Vozilo novo = new Vozilo(el[0], el[1], Float.parseFloat(el[2]), Float.parseFloat(el[3]),
            Integer.parseInt(el[4]));

        if (provjera(novo, lista)) {
          lista.add(novo);
        }
      } catch (Exception e) {
        brojpogresaka++;
        Logger.getGlobal().log(Level.INFO, "\tERROR: POGRESKA PRI CITANJU DATOTEKE VOZILA");
      }
    }

    Collections.sort(lista, Comparator.comparingInt(Vozilo::getRedoslijed));
    listaVozila = lista;
  }

  private boolean provjera(Vozilo novo, List<Vozilo> lista) {

    for (Vozilo vozilo : lista) {
      if (novo.Registracija.equals(vozilo.Registracija)) {
        brojpogresaka++;
        Logger.getGlobal().log(Level.INFO, "\tERROR: Ponovljena registracija");
        return false;
      }

    }

    if (novo.KapacitetKG <= 0 || novo.KapacitetKG <= 0 || novo.Redoslijed <= 0) {
      brojpogresaka++;
      Logger.getGlobal().log(Level.INFO, "\tERROR: Neispravni podaci kapaciteta ili redoslijeda");
      return false;
    }

    return true;
  }

  @Override
  public List<Vozilo> getLista() {
    return listaVozila;
  }

}
