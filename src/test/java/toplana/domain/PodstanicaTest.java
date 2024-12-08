package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class PodstanicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Podstanica.class);
        Podstanica podstanica1 = new Podstanica();
        podstanica1.setId(1L);
        Podstanica podstanica2 = new Podstanica();
        podstanica2.setId(podstanica1.getId());
        assertThat(podstanica1).isEqualTo(podstanica2);
        podstanica2.setId(2L);
        assertThat(podstanica1).isNotEqualTo(podstanica2);
        podstanica1.setId(null);
        assertThat(podstanica1).isNotEqualTo(podstanica2);
    }
}
