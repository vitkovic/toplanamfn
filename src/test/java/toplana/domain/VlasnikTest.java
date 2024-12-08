package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class VlasnikTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vlasnik.class);
        Vlasnik vlasnik1 = new Vlasnik();
        vlasnik1.setId(1L);
        Vlasnik vlasnik2 = new Vlasnik();
        vlasnik2.setId(vlasnik1.getId());
        assertThat(vlasnik1).isEqualTo(vlasnik2);
        vlasnik2.setId(2L);
        assertThat(vlasnik1).isNotEqualTo(vlasnik2);
        vlasnik1.setId(null);
        assertThat(vlasnik1).isNotEqualTo(vlasnik2);
    }
}
