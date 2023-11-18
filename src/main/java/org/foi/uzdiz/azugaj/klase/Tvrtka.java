package org.foi.uzdiz.azugaj.klase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.azugaj.datoteke.DatotekaFactory;
import org.foi.uzdiz.azugaj.podaci.Paket;
import org.foi.uzdiz.azugaj.podaci.Posiljka;
import org.foi.uzdiz.azugaj.podaci.Vozilo;

public class Tvrtka {

  public String vrstaPaketa;
  public String popisVozila;
  public String popisPaketa;
  public String virtualniSat;
  public String mnoziteljSekunde;
  public String maksimalnaTezina;
  public String vrijemeIsporuke;
  public String pocetakRada;
  public String krajRada;

  public List<Vozilo> vozila;
  public List<Paket> vrstePaketa;
  public List<Posiljka> posiljke;
  public List<Posiljka> ZaprimljenePosiljke = new ArrayList();
  public List<Posiljka> DostavljenePosiljke = new ArrayList();

  private static Tvrtka instanca;
  public VirtualnoVrijeme virtualnoVrijeme;

  public boolean rad = true;

  private Tvrtka() {}

  public static Tvrtka getInstance() {

    if (instanca == null)
      instanca = new Tvrtka();
    return instanca;
  }

  public boolean ucitajDatoteke() {

    virtualnoVrijeme = VirtualnoVrijeme.getInstance();
    virtualnoVrijeme.postaviVrijeme(virtualniSat.replace("'", ""), pocetakRada.replace("'", ""),
        krajRada.replace("'", ""));
    virtualnoVrijeme.mnoziteljSekunde = mnoziteljSekunde;

    DatotekaFactory factory = new DatotekaFactory();

    vozila = factory.stvoriDatoteku(popisVozila);
    Logger.getGlobal().log(Level.INFO, "VOZILA UCITANA, broj zapisa: " + vozila.size());
    vrstePaketa = factory.stvoriDatoteku(vrstaPaketa);
    Logger.getGlobal().log(Level.INFO, "VRSTE UCITANAb, broj zapisa: " + vrstePaketa.size());
    posiljke = factory.stvoriDatoteku(popisPaketa);
    Logger.getGlobal().log(Level.INFO, "POSILJKE UCITANA, broj zapisa: " + posiljke.size());

    return true;
  }

  public boolean radTvrtke() {

    while (rad) {
      System.out.print("\nUnos naredbe: ");
      String naredba = System.console().readLine();

      if (naredba.contains("VR ")) {
        simulirajRad(naredba);
      } else if (naredba.equals("IP")) {
        tablice(naredba);
      } else if (naredba.equals("Q")) {
        rad = false;
      } else {
        System.out.print("\n\tNEPOZNATA NAREDBA!");
      }

    }
    return true;
  }

  private void tablice(String naredba) {
    System.out.print("\n\nZAPRIMLJENE POSILJKE");
    System.out.print(
        "\n--------------------------------------------------------------------------------------");

    List<Posiljka> zaIspis = new ArrayList<>();
    if (!ZaprimljenePosiljke.isEmpty())
      zaIspis.addAll(ZaprimljenePosiljke);
    for (Vozilo vozilo : vozila) {
      if (!vozilo.posiljkeZaDostavu().isEmpty())
        zaIspis.addAll(vozilo.posiljkeZaDostavu());
    }
    for (Posiljka p : zaIspis) {
      System.out
          .print("\nVijeme prijema: " + p.vrijemePrijema + "  Vrsta Paketa: " + p.vrstaPaketa.Oznaka
              + "  Vrsta usluge: " + p.uslugaDostave + "  Vrijeme preuzimanja: --  Iznos Dostave: "
              + p.vrstaPaketa.Cijena + "  Iznos pouzeca: " + p.iznosPouzeca);
    }

    System.out.print("\n\nZAPRIMLJENE POSILJKE");
    System.out.print(
        "\n--------------------------------------------------------------------------------------");
    for (Posiljka p : DostavljenePosiljke) {
      System.out.print("\nVijeme prijema: " + p.vrijemePrijema + "  Vrsta Paketa: "
          + p.vrstaPaketa.Oznaka + "  Vrsta usluge: " + p.uslugaDostave + "  Vrijeme preuzimanja: "
          + p.vrijemePreuzimanja + "  Iznos Dostave: " + p.vrstaPaketa.Cijena + "  Iznos pouzeca: "
          + p.iznosPouzeca);
    }

  }

  private void simulirajRad(String naredba) {
    String[] odvojeno = naredba.split(" ");
    if (odvojeno.length != 2 || !odvojeno[1].matches("\\d+")) {
      System.out.print("\n\n\tERROR: KRIVO UNESENA NAREDBA\n");
      return;
    }
    int brojKoraka = (Integer.parseInt(odvojeno[1]) * 60 * 60) / Integer.parseInt(mnoziteljSekunde);

    boolean unutarRada = true;
    for (int i = 0; i < brojKoraka; i++) {

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      unutarRada = virtualnoVrijeme.dodajSekunde();


      if (!unutarRada) {
        System.out.print("\n\n\tKraj radnog vremena!");
        break;
      }
      System.out
          .print("\n\n\tVIRTUALNO VRIJEME: " + virtualnoVrijeme.dohvatiStringVrijeme() + "\n");

      LocalDateTime sad = virtualnoVrijeme.dohvatiVrijeme();
      List<Posiljka> pristigle = new ArrayList();
      pristigle.addAll(posiljke);
      for (Posiljka pos : pristigle) {
        if (pos.vrijemePrijema.isBefore(sad)) {
          ZaprimljenePosiljke.add(pos);
          posiljke.remove(pos);
          System.out.print("\nZaprimljena posiljka: " + pos.oznaka);
        } else {
          break;
        }
      }

      List<Posiljka> zaDostavuList = new ArrayList<>();
      List<Posiljka> manjeHitni = new ArrayList<>();

      for (Posiljka pos : ZaprimljenePosiljke) {
        if (pos.uslugaDostave.equals("H"))
          zaDostavuList.add(pos);
        else {
          manjeHitni.add(pos);
        }
      }
      zaDostavuList.addAll(manjeHitni);

      for (Posiljka pos : zaDostavuList) {
        for (Vozilo vozilo : vozila) {
          if (vozilo.utovari(pos)) {
            System.out
                .print("\nStavljena posiljka: " + pos.oznaka + " u vozilo: " + vozilo.Registracija);
            break;
          }
        }
      }

      int prije = sad.minusSeconds(Integer.parseInt(mnoziteljSekunde)).getHour();
      int poslije = sad.getHour();
      if ((prije != poslije) && (i > 1)) {
        for (Vozilo vozilo : vozila) {
          vozilo.provjeraDostave();
        }
      }
      for (Vozilo vozilo : vozila) {
        vozilo.dostavi();
      }
    }


  }


}
