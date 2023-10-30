import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public String print(Invoice invoice,HashMap<String, Play> plays, PrintFormat format) {
    switch (format) {
      case TEXT:
        return this.printText(invoice,plays);
      case HTML:
        return this.printHTML(invoice,plays);
      default:
        throw new Error("unknown print format: ${format}");
    }

  } 
  public String printText(Invoice invoice, HashMap<String, Play> plays) {
    double totalAmount = 0;
    int volumeCredits = 0;
    
    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer.name));

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);

      double thisAmount =Play.CalculateAmount(perf.audience, play.type);
      volumeCredits += Play.CalculateVolumeCreditsIncrease(perf.audience,play.type);

      // print line for this order
      result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience));
      totalAmount += thisAmount;
    }
    result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount)));
    result.append(String.format("You earned %s credits\n", volumeCredits));
    invoice.customer.Addsolde(volumeCredits);

    if (ReductionApplicable(totalAmount,volumeCredits)){
      invoice.customer.updateSolde();
      result.append(
        "--------------------------------------------\n"+
        "A loyalty discount of €15 has been applied.\n");
      result.append(String.format("Amount owed after discount: %s\n",frmt.format(totalAmount-15)));
      result.append(String.format("Credit after discount: %s ",String.valueOf(volumeCredits-150)));
    }   
    return result.toString();
  }

  public String printHTML(Invoice invoice, HashMap<String,Play> plays) {

    StringBuilder result = new StringBuilder();
    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    double totalAmount = 0;
    int volumeCredits = 0;

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);


      double thisAmount = Play.CalculateAmount(perf.audience,play.type);

      volumeCredits += Play.CalculateVolumeCreditsIncrease(perf.audience,play.type);

      // append line for this order
      result.append(
        String.format(
          "<tr> <td>%s</td> <td>%s</td> <td>%s </td></tr> ",
          play.name,
          frmt.format(thisAmount),
          perf.audience
        )
      );
      totalAmount += thisAmount;
    }
    result.append(
      String.format("<tr><td>Total owed</td><td>%s</td></tr>", frmt.format(totalAmount))
    );
    result.append(String.format("<tr><td>Fidelity points earned</td><td>%s</td></tr>", volumeCredits));
    invoice.customer.Addsolde(volumeCredits);
    String discount = "";
    if (ReductionApplicable(totalAmount,volumeCredits)){ 
      invoice.customer.updateSolde();
      discount = 
          "<p>A loyalty discount of €15 has been applied.</p>\n" + //
          String.format("    <p>Amount owed after discount: %s</p>\n",frmt.format(totalAmount-15)) + //
          String.format("    <p>Credit after discount: %s</p>",String.valueOf(volumeCredits-150));
    } 
    String htmlContent = 
      "<!DOCTYPE html>\n" +
        "<html>\n" +
          "<head>\n" +
            "<title>Generated HTML File</title>\n" +
            "<style> table, th, td {border: 1px solid black;}</style>"+
          "</head>\n" +
            "<body>\n" +
            "<h1>" + "Invoice" + "</h1>" +
            "<p>" + "Client :" + invoice.customer.name + "</p>" +
            "<table>"+
              "<tr> "+ "<th>"+"Piece"+"</th>"+"<th>"+"Price"+"</th>"+"<th>"+"Seats Solde"+"</th>"+"</tr>"+ 
              result +
            "</table>"+
            discount +
          "</body>\n" +
        "</html>";
    return htmlContent;
  }

  public boolean ReductionApplicable(double totalAmount,int totalCredit){
    boolean applied = false;
    if (totalCredit>=150 && totalAmount>=15){
      totalCredit -= 150;
      totalAmount -= 15;
      applied = true;
    } 
    return applied;
  } 





}
