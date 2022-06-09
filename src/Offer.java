import java.time.LocalDate;

public class Offer {
    protected String nrOfe;
    protected String client;
    protected String title;
    protected LocalDate dateOfOffer;
    protected LocalDate  dateOfExecution;
    protected Double price;
    protected String description;
    Offer(String client){
        this.client = client;
       }

       public void setNrOfe(String nrOfe){
        this.nrOfe = nrOfe;
       }
       public String getNrOfe(){
        return nrOfe;
       }
       public void setClient( String client){
        this.client = client;
       }
       public String getClient(){
        return client;
       }
       public void setPrice(Double price){
        this.price = price;
       }
       public double getPrice(){
        return price;
       }
       public void setTitle(String title){
        this.title=title;
       }
       public String getTitle(){
        return title;
       }
       public void setDateOfOffer(LocalDate  dateOfOffer, int weeks){
        this.dateOfOffer = dateOfOffer;
        this.dateOfExecution = dateOfOffer.plusWeeks(weeks);
       }
       public LocalDate getDateOfOffer(){

        return dateOfOffer;
       }
       public void setDateOfExecution(LocalDate dateOfExecution){
        this.dateOfExecution= dateOfExecution;
       }
       public LocalDate getDateOfExecution(){
        return dateOfExecution;
       }
       public void setDescription(String description){
        this.description = description;
       }
       public String getDescription(){
        return description;
       }
    }