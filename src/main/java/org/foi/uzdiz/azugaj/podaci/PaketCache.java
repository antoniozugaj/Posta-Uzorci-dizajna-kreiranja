package org.foi.uzdiz.azugaj.podaci;

import java.util.ArrayList;
import java.util.List;

public final class PaketCache {

  private static PaketCache instanca;
  public List<Paket> paketi = new ArrayList();

  private PaketCache() {

  }

  public static PaketCache getInstance() {

    if (instanca == null)
      instanca = new PaketCache();
    return instanca;
  }

  public void spremiPakete(List<Paket> lista) {
    paketi = lista;
  }

  public Paket klonirajPaket(String oznaka) {

    for (Paket paket : paketi) {
      if (paket.Oznaka.equals(oznaka))
        return paket.clone();
    }
    return null;
  }

}
