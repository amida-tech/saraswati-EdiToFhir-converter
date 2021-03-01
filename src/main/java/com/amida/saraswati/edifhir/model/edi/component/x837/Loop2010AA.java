/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Loop2010AA {
    private NM1 billingProviderName;
    private N3 address;
    private N4 cityStateZip;
    private REF taxId;
    private List<PER> contactInfo;

}
