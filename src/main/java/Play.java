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
  
}
