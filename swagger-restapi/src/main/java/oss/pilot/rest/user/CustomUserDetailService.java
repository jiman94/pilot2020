package oss.pilot.rest.user;

import lombok.RequiredArgsConstructor;
import oss.pilot.common.CacheKey;
import oss.pilot.exception.CUserNotFoundException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    @Cacheable(value = CacheKey.USER, key = "#userPk", unless = "#result == null")
    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
    }
}
