/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.CUR;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.HL;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.PRV;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Loop2000A {
    private HL billingProviderLevel;  // required
    private PRV providerSpecialityInfo;
    private CUR currencyInfo;

    private Loop2010AA providerName;
    private Loop2010AB payToAddress;
    private Loop2010AC payToPlanName;
    private Loop2010AD payToFactoringAgentName;

}
