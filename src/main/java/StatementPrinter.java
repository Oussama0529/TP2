import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public StringBuffer print(Invoice invoice, HashMap<String, Play> plays) {
    int totalAmount = 0;
    int volumeCredits = 0;

    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer));

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);

      int thisAmount =CalculateAmount(perf.audience, play.type);

      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if ("comedy".equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // print line for this order
      result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount / 100), perf.audience));
      totalAmount += thisAmount;
    }
    result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount / 100)));
    result.append(String.format("You earned %s credits\n", volumeCredits));
    return result;
  }

  private int CalculateAmount(int audience,String type){
    int thisAmount  = 0;
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

  private int CalculateAmountTragedy(int audience) {
    int thisAmount = 40000;
    if (audience > 30) {
      thisAmount += 1000 * (audience - 30);
    }
    return thisAmount;
  }  

  private int CalculateAmountComedy(int audience) {
    int thisAmount = 30000;
    if (audience > 20) {
      thisAmount += 10000 + 500 * (audience - 20);
    }
    thisAmount += 300 *audience;
    return thisAmount;
  }  

}
