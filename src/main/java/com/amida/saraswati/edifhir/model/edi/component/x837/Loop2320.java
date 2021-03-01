/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import lombok.Data;

@Data
public class Loop2320 {
    private SBR subscriberInfo;
    private RAS claimAdjustmentInfo;
    private AMT amtCOBPaid;
    private AMT allowedAmt;
    private AMT nonCoveredCOBAmt;
    private AMT patientAmt;
    private OI otherInsCoverageInfo;
    private MOA outpatientAdjudicationInfo;
    private LQ healthcareRemarkCode;

    private Loop2330A otherSubscriberName;
    private Loop2330B otherPayerName;
    private Loop2330C otherPayerReferingProvider;
    private Loop2330D otherPayerRenderingProvider;
    private Loop2330E otherPayerServiceLocation;
    private Loop2330F otherPayerSupervisingProvider;
    private Loop2330G otherPayerBillingProvider;
}
