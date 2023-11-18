package org.foi.uzdiz.azugaj.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatotekaFactory {

  public <T> List<T> stvoriDatoteku(String putanja) {

    List<String[]> sadrzaj = null;
    Datoteka datoteka = null;
    try (BufferedReader fileReader = new BufferedReader(new FileReader(putanja))) {

      sadrzaj = new ArrayList<>();
      String red = "";
      boolean isIspravno = true;

      String[] prviRed = fileReader.readLine().split(";");

      while ((red = fileReader.readLine()) != null) {
        red = red.replace(",", ".");
        String[] elementi = red.split(";");

        isIspravno = true;
        for (String atribut : elementi)
          if (atribut == null || atribut.equals(""))
            isIspravno = false;

        if (elementi.length == prviRed.length && isIspravno)
          sadrzaj.add(elementi);
      }

      datoteka = odaberiDatoteku(prviRed, sadrzaj);


    } catch (IOException e) {
      Logger.getGlobal().log(Level.INFO, "\tERROR: POGRESKA PRI CITANJU DATOTEKE");
      e.printStackTrace();
    }

    return datoteka.getLista();
  }



  private Datoteka odaberiDatoteku(String[] prviRed, List<String[]> sadrzaj) {

    Datoteka datoteka = null;
    if (usporedi(prviRed,
        "Registracija;Opis;Kapacitet težine u kg;Kapacitet prostora u m3;Redoslijed"))
      datoteka = new DatotekaVozila(sadrzaj);
    else if (usporedi(prviRed,
        "Oznaka;Opis;Visina;Širina;Dužina;Maksimalna težina;Cijena;Cijena hitno;CijenaP;CijenaT"))
      datoteka = new DatotekaVrste(sadrzaj);
    else if (usporedi(prviRed,
        "Oznaka;Vrijeme prijema;Pošiljatelj;Primatelj;Vrsta paketa;Visina;Širina;Dužina;Težina;Usluga dostave;Iznos pouzeća"))
      datoteka = new DatotekaPaketi(sadrzaj);

    return datoteka;
  }

  private boolean usporedi(String[] prviRed, String atributi) {
    String[] atributiSplit = atributi.split(";");

    if (prviRed.length != atributiSplit.length)
      return false;

    for (int i = 0; i < prviRed.length; i++) {
      if (!prviRed[i].equals(atributiSplit[i])) {
        return false;
      }
    }
    return true;
  }

}
