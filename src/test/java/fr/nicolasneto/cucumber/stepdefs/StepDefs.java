package fr.nicolasneto.cucumber.stepdefs;

import fr.nicolasneto.VerifyMyCandidateApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = VerifyMyCandidateApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
