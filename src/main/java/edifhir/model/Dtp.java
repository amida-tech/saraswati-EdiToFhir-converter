package edifhir.model;

public class Dtp {
   private String dateTimeQualifier;
   private String dateTimePFQualifier;
   private String dateTimePeriod;

   public Dtp(String dateTimeQualifier, String dateTimePFQualifier){
       this.dateTimeQualifier = dateTimeQualifier;
       this.dateTimePFQualifier = dateTimePFQualifier;
   }
   
   public Dtp(String dateTimeQualifier, String dateTimePFQualifier, String dateTimePeriod){
     this.dateTimeQualifier = dateTimeQualifier;
     this.dateTimePFQualifier = dateTimePFQualifier;
     this.dateTimePeriod = dateTimePeriod;
   }


  public String getDateTimeQualifier() {
    return this.dateTimeQualifier;
  }

  public void setDateTimeQualifier(String dateTimeQualifier) {
    this.dateTimeQualifier = dateTimeQualifier;
  }

  public String getDateTimePFQualifier() {
    return this.dateTimePFQualifier;
  }

  public void setDateTimePFQualifier(String dateTimePFQualifier) {
    this.dateTimePFQualifier = dateTimePFQualifier;
  }

  public String getDateTimePeriod() {
    return this.dateTimePeriod;
  }

  public void setDateTimePeriod(String dateTimePeriod) {
    this.dateTimePeriod = dateTimePeriod;
  }

}
