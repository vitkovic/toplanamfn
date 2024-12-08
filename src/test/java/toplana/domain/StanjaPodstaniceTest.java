package toplana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import toplana.web.rest.TestUtil;

public class StanjaPodstaniceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StanjaPodstanice.class);
        StanjaPodstanice stanjaPodstanice1 = new StanjaPodstanice();
        stanjaPodstanice1.setId(1L);
        StanjaPodstanice stanjaPodstanice2 = new StanjaPodstanice();
        stanjaPodstanice2.setId(stanjaPodstanice1.getId());
        assertThat(stanjaPodstanice1).isEqualTo(stanjaPodstanice2);
        stanjaPodstanice2.setId(2L);
        assertThat(stanjaPodstanice1).isNotEqualTo(stanjaPodstanice2);
        stanjaPodstanice1.setId(null);
        assertThat(stanjaPodstanice1).isNotEqualTo(stanjaPodstanice2);
    }
}
