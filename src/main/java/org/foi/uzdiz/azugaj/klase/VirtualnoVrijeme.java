package org.foi.uzdiz.azugaj.klase;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VirtualnoVrijeme {

  public String mnoziteljSekunde;
  public LocalTime pocetakRada;
  public LocalTime krajRada;
  public LocalDateTime virtualniSat;

  private static VirtualnoVrijeme instanca;

  private VirtualnoVrijeme() {

  }

  public static VirtualnoVrijeme getInstance() {

    if (instanca == null)
      instanca = new VirtualnoVrijeme();
    return instanca;
  }

  public void postaviVrijeme(String Sat, String pocetak, String kraj) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    virtualniSat = LocalDateTime.parse(Sat, formatter);

    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
    krajRada = LocalTime.parse(kraj, formatter2);
    pocetakRada = LocalTime.parse(pocetak, formatter2);
  }

  public boolean dodajSekunde() {
    virtualniSat = virtualniSat.plusSeconds(Long.parseLong(mnoziteljSekunde));
    return provjeraRadnogVremena();
  }

  private boolean provjeraRadnogVremena() {
    LocalTime vrijeme = virtualniSat.toLocalTime();
    if (vrijeme.isAfter(krajRada)) {
      virtualniSat = virtualniSat.plusDays(1).withHour(pocetakRada.getHour())
          .withMinute(pocetakRada.getMinute()).withSecond(pocetakRada.getSecond());
      return false;
    }
    return true;
  }

  public LocalDateTime dohvatiVrijeme() {
    return virtualniSat;
  }

  public String dohvatiStringVrijeme() {
    return DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss").format(virtualniSat);
  }

}
