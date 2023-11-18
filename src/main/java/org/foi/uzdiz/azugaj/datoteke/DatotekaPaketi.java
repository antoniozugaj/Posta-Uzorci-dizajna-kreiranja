package org.foi.uzdiz.azugaj.datoteke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.azugaj.podaci.Paket;
import org.foi.uzdiz.azugaj.podaci.PaketCache;
import org.foi.uzdiz.azugaj.podaci.Posiljka;

public class DatotekaPaketi extends Datoteka {

  List<Posiljka> listaPosiljka;
  int brojpogresaka = 0;

  public DatotekaPaketi(List<String[]> sadrzaj) {
    List<Posiljka> lista = new ArrayList();

    for (String[] el : sadrzaj) {
      try {
        Posiljka novo = new Posiljka();

        novo.oznaka = el[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        novo.vrijemePrijema = LocalDateTime.parse(el[1], formatter);

        novo.posiljatelj = el[2];
        novo.primatelj = el[3];
        novo.visina = Float.parseFloat(el[5]);
        novo.sirina = Float.parseFloat(el[6]);
        novo.duzina = Float.parseFloat(el[7]);
        novo.tezina = Float.parseFloat(el[8]);
        novo.uslugaDostave = el[9];
        novo.iznosPouzeca = Float.parseFloat(el[10]);
        novo.vrijemePreuzimanja = null;

        PaketCache paketCache = PaketCache.getInstance();
        Paket noviPaket = paketCache.klonirajPaket(el[4]);

        if (novo.visina != 0 && novo.duzina != 0 && novo.sirina != 0 && novo.tezina != 0) {

          // if (!provjeraVelicine(novo, noviPaket))
          // continue;

          noviPaket.Visina = novo.visina;
          noviPaket.Duzina = novo.duzina;
          noviPaket.Sirina = novo.sirina;
          noviPaket.Tezina = novo.tezina;
          noviPaket.izracunajCijenu();
          if (novo.oznaka.equals("H"))
            noviPaket.Cijena = noviPaket.CijenaHitno;
        }


        novo.vrstaPaketa = noviPaket;

        if (!provjera(novo, lista))
          continue;

        lista.add(novo);
      } catch (Exception e) {
        brojpogresaka++;
        Logger.getGlobal().log(Level.INFO, "\tERROR: POGRESKA PRI CITANJU DATOTEKA PAKETA");
      }
    }

    listaPosiljka = lista;
  }

  private boolean provjeraVelicine(Posiljka novo, Paket noviPaket) {

    if (novo.tezina > noviPaket.MaksTezina || novo.sirina > noviPaket.Sirina
        || novo.duzina > noviPaket.Duzina || novo.visina > noviPaket.Visina) {
      Logger.getGlobal().log(Level.INFO, "\tERROR: Paket je pre velik ili pre tezak");
      return false;
    }
    return true;
  }

  private boolean provjera(Posiljka novo, List<Posiljka> lista) {

    boolean ispravno = true;

    if (!(novo.uslugaDostave.contains("H") || novo.uslugaDostave.contains("S")
        || novo.uslugaDostave.contains("P") || novo.uslugaDostave.contains("R"))) {
      Logger.getGlobal().log(Level.INFO, "\tERROR: Nepoznata usluga");
      return false;
    }

    for (Posiljka posiljka : lista) {
      if (posiljka.oznaka.equals(novo.oznaka)) {
        Logger.getGlobal().log(Level.INFO, "\tERROR: Ponavljanje oznake paketa");
        return false;
      }
    }



    return ispravno;
  }

  @Override
  public List<Posiljka> getLista() {
    return listaPosiljka;
  }

}
