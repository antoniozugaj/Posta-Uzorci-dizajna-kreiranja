package org.foi.uzdiz.azugaj.podaci;

public abstract class Paket {
  public String Oznaka;
  public String Opis;
  public float Visina;
  public float Sirina;
  public float Duzina;
  public float MaksTezina;
  public float Tezina;
  public float Cijena;
  public float CijenaHitno;


  public abstract Paket clone();

  public abstract void izracunajCijenu();

}
