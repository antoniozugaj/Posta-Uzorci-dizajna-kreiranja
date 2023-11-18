package org.foi.uzdiz.azugaj.klase;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) {

    String argumenti = String.join(" ", args);

    if (!argumenti.contains("--vp") || !argumenti.contains("--pv") || !argumenti.contains("--pp")
        || !argumenti.contains("--mt") || !argumenti.contains("--vi") || !argumenti.contains("--vs")
        || !argumenti.contains("--ms") || !argumenti.contains("--pr")
        || !argumenti.contains("--kr")) {
      Logger.getGlobal().log(Level.INFO, "\tERROR: NISU UPISANI SVI ARGUMENTI");
      return;
    }
    Pattern patternVP = Pattern.compile("(?<vpFlag>--vp)\\s(?<vpValue>\\w+\\.csv)");
    Pattern patternPV = Pattern.compile("(?<pvFlag>--pv)\\s(?<pvValue>\\w+\\.csv)");
    Pattern patternPP = Pattern.compile("(?<ppFlag>--pp)\\s(?<ppValue>\\w+\\.csv)");
    Pattern patternMT = Pattern.compile("(?<mtFlag>--mt)\\s(?<mtValue>\\d+)");
    Pattern patternVI = Pattern.compile("(?<viFlag>--vi)\\s(?<viValue>\\d+)");
    Pattern patternVS = Pattern.compile(
        "(?<vsFlag>--vs)\\s(?<vsValue>(([01][0-9]|2[0-3]):[0-5][0-9])|(([1-9]|([012][0-9])|(3[01]))\\.([0]{0,1}[1-9]|1[012])\\.\\d\\d\\d\\d\\.\\s([0-1]?[0-9]|2?[0-3]):([0-5]\\d):([0-5]\\d)))");
    Pattern patternMS = Pattern.compile("(?<msFlag>--ms)\\s(?<msValue>\\d+)");
    Pattern patternPR =
        Pattern.compile("(?<prFlag>--pr)\\s(?<prValue>([01][0-9]|2[0-3]):[0-5][0-9])");
    Pattern patternKR =
        Pattern.compile("(?<krFlag>--kr)\\s(?<krValue>([01][0-9]|2[0-3]):[0-5][0-9])");


    Tvrtka tvrtka = Tvrtka.getInstance();

    Matcher matcher = patternVP.matcher(argumenti);

    if (matcher.find()) {
      tvrtka.vrstaPaketa = matcher.group("vpValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --vp");
      return;
    }
    matcher = patternPV.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.popisVozila = matcher.group("pvValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --pv");
      return;
    }
    matcher = patternPP.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.popisPaketa = matcher.group("ppValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --pp");
      return;
    }
    matcher = patternMT.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.maksimalnaTezina = matcher.group("mtValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --mt");
      return;
    }
    matcher = patternVI.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.vrijemeIsporuke = matcher.group("viValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --vi");
      return;
    }
    matcher = patternVS.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.virtualniSat = matcher.group("vsValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --vs");
      return;
    }
    matcher = patternMS.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.mnoziteljSekunde = matcher.group("msValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --ms");
      return;
    }
    matcher = patternPR.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.pocetakRada = matcher.group("prValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --pr");
      return;
    }
    matcher = patternKR.matcher(argumenti);
    if (matcher.find()) {
      tvrtka.krajRada = matcher.group("krValue");
    } else {
      Logger.getGlobal().log(Level.INFO, "\tERROR: KRIVO UNESEN PODATAK --kr");
      return;
    }

    tvrtka.ucitajDatoteke();
    tvrtka.radTvrtke();

    System.out.print("\nProgram normalno zavrsio");
    return;
  }

}
