import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

      double thisAmount =Play.CalculateAmount(perf.audience, play.type);
      volumeCredits += Play.CalculateVolumeCreditsIncrease(perf.audience,play.type);

      // print line for this order
      result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience));
      totalAmount += thisAmount;
    }
    result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount)));
    result.append(String.format("You earned %s credits\n", volumeCredits));
    return result;
  }

  public String toHtml(Invoice invoice, HashMap<String,Play> plays) {

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
 

    String htmlContent = 
      "<!DOCTYPE html>\n" +
        "<html>\n" +
          "<head>\n" +
            "<title>Generated HTML File</title>\n" +
            "<style> table, th, td {border: 1px solid black;}</style>"+
          "</head>\n" +
            "<body>\n" +
            "<h1>" + "Invoice" + "</h1>" +
            "<p>" + "Client :" + invoice.customer + "</p>" +
            "<table>"+
              "<tr> "+ "<th>"+"Piece"+"</th>"+"<th>"+"Price"+"</th>"+"<th>"+"Seats Solde"+"</th>"+"</tr>"+ 
              result +
            "</table>"+
          "</body>\n" +
        "</html>";
    return htmlContent;
  }


//  public static void main(String[] args) {
//          String htmlFilePath = "/home/local.isima.fr/ounajib/Desktop/TP2gl/src/test/java/StatementPrinterTests.html.approved.txt"; // Path to the output HTML file
//          HashMap<String, Play> plays = new HashMap<>();
//         plays.put("hamlet",  new Play("Hamlet", "tragedy"));
//         plays.put("as-like",  new Play("As You Like It", "comedy"));
//         plays.put("othello",  new Play("Othello", "tragedy"));

//         Invoice invoice = new Invoice("BigCo", List.of(
//                 new Performance("hamlet", 55),
//                 new Performance("as-like", 35),
//                 new Performance("othello", 40)));

//             toHtml(invoice, plays, htmlFilePath);
//      }



}
