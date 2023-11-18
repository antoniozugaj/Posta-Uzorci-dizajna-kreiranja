package org.foi.uzdiz.azugaj.datoteke;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.azugaj.klase.Tvrtka;
import org.foi.uzdiz.azugaj.podaci.NeStandardniPaket;
import org.foi.uzdiz.azugaj.podaci.Paket;
import org.foi.uzdiz.azugaj.podaci.PaketCache;
import org.foi.uzdiz.azugaj.podaci.StandardniPaket;

public class DatotekaVrste extends Datoteka {

  List<Paket> listaPaketa;
  int brojpogresaka = 0;

  public DatotekaVrste(List<String[]> sadrzaj) {
    List<Paket> lista = new ArrayList();

    for (String[] el : sadrzaj) {
      try {

        float cijenaP = Float.parseFloat(el[8]);
        float cijenaT = Float.parseFloat(el[9]);

        if (cijenaP != 0 && cijenaT != 0) {
          NeStandardniPaket novi = new NeStandardniPaket();
          novi.Oznaka = el[0];
          novi.Opis = el[1];
          novi.Cijena = Float.parseFloat(el[6]);
          novi.CijenaHitno = Float.parseFloat(el[7]);
          novi.CijenaP = cijenaP;
          novi.CijenaT = cijenaT;
          novi.MaksTezina = Float.parseFloat(Tvrtka.getInstance().maksimalnaTezina);

          // TODO provjera podataka

          lista.add(novi);
        } else {
          StandardniPaket novi = new StandardniPaket();

          novi.Oznaka = el[0];
          novi.Opis = el[1];
          novi.Visina = Float.parseFloat(el[2]);
          novi.Sirina = Float.parseFloat(el[3]);
          novi.Duzina = Float.parseFloat(el[4]);
          novi.MaksTezina = Float.parseFloat(el[5]);
          novi.Cijena = Float.parseFloat(el[6]);
          novi.CijenaHitno = Float.parseFloat(el[7]);

          // TODO provjera podataka

          lista.add(novi);
        }
      } catch (Exception e) {
        brojpogresaka++;
        Logger.getGlobal().log(Level.INFO, "\tERROR: POGRESKA PRI CITANJU DATOTEKE VRSTA");
      }
    }

    listaPaketa = lista;

    PaketCache registar = PaketCache.getInstance();
    registar.spremiPakete(lista);
  }

  @Override
  public List<Paket> getLista() {
    return listaPaketa;
  }

}
