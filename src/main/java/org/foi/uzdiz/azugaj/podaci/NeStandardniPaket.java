package org.foi.uzdiz.azugaj.podaci;

public class NeStandardniPaket extends Paket {

  public float CijenaP;
  public float CijenaT;

  public NeStandardniPaket() {

  }

  public NeStandardniPaket(NeStandardniPaket paket) {
    Oznaka = paket.Oznaka;
    Opis = paket.Opis;
    Cijena = paket.Cijena;
    CijenaHitno = paket.CijenaHitno;
    CijenaP = paket.CijenaP;
    CijenaT = paket.CijenaT;

  }

  @Override
  public Paket clone() {
    return new NeStandardniPaket(this);
  }

  public void izracunajCijenu() {
    Cijena = CijenaP * (Visina * Sirina * Duzina) + CijenaT * Tezina;
  }

}
