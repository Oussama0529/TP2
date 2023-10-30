public class Customer {
    public String name ;
    public int clientNumber;
    public int solde;



    public Customer(String name,int clientNumber,int solde){
        this.name = name ;
        this.clientNumber = clientNumber;
        this.solde = solde ;
    }

    public void Addsolde(int solde){
        this.solde += solde;
    } 
    public void updateSolde(){
        this.solde -= 150;
    }




}
