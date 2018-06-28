package fr.nicolasneto.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fr.nicolasneto.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(fr.nicolasneto.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobOffer.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobOffer.class.getName() + ".jobsjobSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobOffer.class.getName() + ".idProfilSkillTests", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobOffer.class.getName() + ".jobjobResponses", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Company.class.getName() + ".companies", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SkillTest.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SkillTest.class.getName() + ".skillTestskillTestResponses", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SkillTest.class.getName() + ".skillTestSkillQuestions", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SkillTestResponse.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.SkillQuestion.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Skill.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Skill.class.getName() + ".skilljobSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Skill.class.getName() + ".skillcandidateSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Skill.class.getName() + ".skillcandidateTrueSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Skill.class.getName() + ".skillskillQuestions", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobSkill.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.JobResponse.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.CategoryOffer.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.CategoryOffer.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".profilcandidateSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".profilcandidateTrueSkills", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".candidatskillTestResponses", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".candidatjobResponses", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".profiletudeProfils", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Profil.class.getName() + ".profilexperienceProfils", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.EtudeProfil.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Etude.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Etude.class.getName() + ".etudeJobOffers", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Etude.class.getName() + ".etudeetudeProfils", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.ExperienceProfil.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Experience.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.Experience.class.getName() + ".experienceexperienceProfils", jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.CandidateSkill.class.getName(), jcacheConfiguration);
            cm.createCache(fr.nicolasneto.domain.CandidateTrueSkill.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
