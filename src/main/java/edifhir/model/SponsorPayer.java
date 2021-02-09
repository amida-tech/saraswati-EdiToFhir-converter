package edifhir.model;

public class SponsorPayer {
    
    private String entityIdCode;
    private String name;
    private String idCoderQualifier;
    private String idCode;
    private String entityRelationshipCode;
    private String entityIdCode2;

    public SponsorPayer(String entityIdCode, String name, String idCodeQualifier, String idCode, String entityRelationshipCode, String entityIdCode2){
        this.entityIdCode = entityIdCode;
        this.name = name;
        this.idCoderQualifier = idCodeQualifier;
        this.idCode = idCode;
        this.entityRelationshipCode = entityRelationshipCode;
        this.entityIdCode2 = entityIdCode2;
    }


    public String getEntityIdCode() {
        return this.entityIdCode;
    }

    public void setEntityIdCode(String entityIdCode) {
        this.entityIdCode = entityIdCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCoderQualifier() {
        return this.idCoderQualifier;
    }

    public void setIdCoderQualifier(String idCoderQualifier) {
        this.idCoderQualifier = idCoderQualifier;
    }

    public String getIdCode() {
        return this.idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getEntityRelationshipCode() {
        return this.entityRelationshipCode;
    }

    public void setEntityRelationshipCode(String entityRelationshipCode) {
        this.entityRelationshipCode = entityRelationshipCode;
    }

    public String getEntityIdCode2() {
        return this.entityIdCode2;
    }

    public void setEntityIdCode2(String entityIdCode2) {
        this.entityIdCode2 = entityIdCode2;
    }

}
