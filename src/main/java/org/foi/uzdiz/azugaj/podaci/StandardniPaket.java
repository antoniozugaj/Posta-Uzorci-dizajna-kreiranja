package org.foi.uzdiz.azugaj.podaci;

public class StandardniPaket extends Paket {

  public StandardniPaket() {

  }

  public StandardniPaket(StandardniPaket paket) {
    Cijena = paket.Cijena;
    Oznaka = paket.Oznaka;
    Opis = paket.Opis;
    Visina = paket.Visina;
    Sirina = paket.Sirina;
    Duzina = paket.Duzina;
    MaksTezina = paket.MaksTezina;
    CijenaHitno = paket.CijenaHitno;

  }

  @Override
  public Paket clone() {
    return new StandardniPaket(this);
  }

  @Override
  public void izracunajCijenu() {}

}
