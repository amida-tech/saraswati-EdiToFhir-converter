package edifhir.model;

public class Ref{
    
    private String refIdQualifier;
    private String refIdentification;
    private String description;
    private String refIdentifier;

    public Ref(String refIdQualifier, String refIdentification, String description, String refIdentifier) {
        this.refIdQualifier = refIdQualifier;
        this.refIdentification = refIdentification;
        this.description = description;
        this.refIdentifier = refIdentifier;
    }

    public String getRefIdQualifier() {
        return this.refIdQualifier;
    }

    public void setRefIdQualifier(String refIdQualifier) {
        this.refIdQualifier = refIdQualifier;
    }

    public String getRefIdentification() {
        return this.refIdentification;
    }

    public void setRefIdentification(String refIdentification) {
        this.refIdentification = refIdentification;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefIdentifier() {
        return this.refIdentifier;
    }

    public void setRefIdentifier(String refIdentifier) {
        this.refIdentifier = refIdentifier;
    }

    
}
