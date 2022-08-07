package client.config;

import client.session.Session;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        if (Session.getInfoId() != null) {
            return Optional.of(Session.getInfoId());
        }
        return Optional.empty();
    }
}
