/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.N3;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.N4;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.NM1;
import lombok.Data;

@Data
public class Loop2310E {
    private NM1 pickupLocation;
    private N3 pickupAddress;
    private N4 pickupCityStateZip;
}
