package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class StanStanjeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StanStanje.class);
        StanStanje stanStanje1 = new StanStanje();
        stanStanje1.setId(1L);
        StanStanje stanStanje2 = new StanStanje();
        stanStanje2.setId(stanStanje1.getId());
        assertThat(stanStanje1).isEqualTo(stanStanje2);
        stanStanje2.setId(2L);
        assertThat(stanStanje1).isNotEqualTo(stanStanje2);
        stanStanje1.setId(null);
        assertThat(stanStanje1).isNotEqualTo(stanStanje2);
    }
}
