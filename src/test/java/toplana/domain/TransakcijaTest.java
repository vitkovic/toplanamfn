package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class TransakcijaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transakcija.class);
        Transakcija transakcija1 = new Transakcija();
        transakcija1.setId(1L);
        Transakcija transakcija2 = new Transakcija();
        transakcija2.setId(transakcija1.getId());
        assertThat(transakcija1).isEqualTo(transakcija2);
        transakcija2.setId(2L);
        assertThat(transakcija1).isNotEqualTo(transakcija2);
        transakcija1.setId(null);
        assertThat(transakcija1).isNotEqualTo(transakcija2);
    }
}
