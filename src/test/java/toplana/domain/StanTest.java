package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class StanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stan.class);
        Stan stan1 = new Stan();
        stan1.setId(1L);
        Stan stan2 = new Stan();
        stan2.setId(stan1.getId());
        assertThat(stan1).isEqualTo(stan2);
        stan2.setId(2L);
        assertThat(stan1).isNotEqualTo(stan2);
        stan1.setId(null);
        assertThat(stan1).isNotEqualTo(stan2);
    }
}
