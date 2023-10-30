import java.util.*;

public class Invoice {

  public Customer customer;
  public List<Performance> performances;

  public Invoice(String name, List<Performance> performances) {
    this.customer = new Customer(name, 0, 0);
    this.performances = performances;
  }
}
