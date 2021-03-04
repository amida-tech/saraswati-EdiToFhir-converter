package com.amida.saraswati.edifhir.model.x12helper;

import lombok.Data;

/**
 * @author warren
 */
@Data
public class ClaimReferences {
    private String predetermineId;
    private String medicareVersionCode;
    private String priorAuth;
    private String sec4081Id;
    private String mammgrphyCertNo;
    private String referalNumber;
    private String payerClaimCntrlNo;
    private String cliaNo;
    private String repricedClaimNumber;

    private String claimNumber;  // claim id for transmission intermediaries.

}
