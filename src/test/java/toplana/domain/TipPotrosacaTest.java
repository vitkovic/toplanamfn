package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class TipPotrosacaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipPotrosaca.class);
        TipPotrosaca tipPotrosaca1 = new TipPotrosaca();
        tipPotrosaca1.setId(1L);
        TipPotrosaca tipPotrosaca2 = new TipPotrosaca();
        tipPotrosaca2.setId(tipPotrosaca1.getId());
        assertThat(tipPotrosaca1).isEqualTo(tipPotrosaca2);
        tipPotrosaca2.setId(2L);
        assertThat(tipPotrosaca1).isNotEqualTo(tipPotrosaca2);
        tipPotrosaca1.setId(null);
        assertThat(tipPotrosaca1).isNotEqualTo(tipPotrosaca2);
    }
}
