import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public StringBuffer print(Invoice invoice, HashMap<String, Play> plays) {
    double totalAmount = 0;
    int volumeCredits = 0;

    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer));

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);

      double thisAmount =CalculateAmount(perf.audience, play.type);
      volumeCredits += CalculateVolumeCreditsIncrease(perf.audience,play.type);

      // print line for this order
      result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience));
      totalAmount += thisAmount;
    }
    result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount)));
    result.append(String.format("You earned %s credits\n", volumeCredits));
    return result;
  }

  private int CalculateVolumeCreditsIncrease(int audience,String type){
    int volumeCreditsIncrease =0 ;
    
    volumeCreditsIncrease += Math.max(audience - 30, 0);

    if ("comedy".equals(type)) volumeCreditsIncrease += Math.floor(audience / 5);

    return volumeCreditsIncrease;
  }     
  private double CalculateAmount(int audience,String type){
    double thisAmount  = 0;
    switch (type) {
        case "tragedy":
          thisAmount = CalculateAmountTragedy(audience);
          break;
        case "comedy":
          thisAmount = CalculateAmountComedy(audience);
          break;
        default:
          throw new Error("unknown type: ${play.type}");
      }
    return thisAmount;
  } 

  private double CalculateAmountTragedy(int audience) {
    double thisAmount = 400;
    if (audience > 30) {
      thisAmount += 10 * (audience - 30);
    }
    return thisAmount;
  }  

  private double CalculateAmountComedy(int audience) {
    double thisAmount = 300;
    if (audience > 20) {
      thisAmount += 100 + 5 * (audience - 20);
    }
    thisAmount += 3 *audience;
    return thisAmount;
  }  

}
