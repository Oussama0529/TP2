import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Play {
  public List<String> typelist = List.of("comedy", "tragedy", "history","pastoral");

  public String name;
  public String type;

  public Play(String name, String type) {
    this.name = name;
    Objects.requireNonNull(typelist.contains(type),"type must not be null ");
    this.type = type;
  }
  
  public static int CalculateVolumeCreditsIncrease(int audience,String type){
    int volumeCreditsIncrease =0 ;
    
    volumeCreditsIncrease += Math.max(audience - 30, 0);

    if ("comedy".equals(type)) volumeCreditsIncrease += Math.floor(audience / 5);

    return volumeCreditsIncrease;
  }     
  public static double CalculateAmount(int audience,String type){
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

  private static double CalculateAmountTragedy(int audience) {
    double thisAmount = 400;
    if (audience > 30) {
      thisAmount += 10 * (audience - 30);
    }
    return thisAmount;
  }  

  private static double CalculateAmountComedy(int audience) {
    double thisAmount = 300;
    if (audience > 20) {
      thisAmount += 100 + 5 * (audience - 20);
    }
    thisAmount += 3 *audience;
    return thisAmount;
  }  


}
