package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.application.SignUpUseCase;
import dev.chan.orderpaymentservice.application.dto.SignUpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    MemberController sut;

    @Mock
    SignUpUseCase signUpUseCase;

    @Captor
    ArgumentCaptor<SignUpCommand> signUpCommandCaptor;

    MockMvc mvc;

    @BeforeEach
    void setUp(){
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mvc = MockMvcBuilders.standaloneSetup(sut)
                .setValidator(validator)
                .build();
    }

    @Test
    void POST_회원가입_성공시_로그인폼을_redirect한다() throws Exception {
        mvc.perform(post("/signup")
                        .param("email", "test@test.com")
                        .param("phone","01011111112")
                        .param("password","1234")
                        .param("passwordConfirm", "1234")
                        .param("name","name")
                        .param("agreeTerms","true")
        ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));

        verify(signUpUseCase).handle(signUpCommandCaptor.capture());

    }

    @Test
    void POST_회원가입_검증_실패시_폼으로_리턴한다() throws Exception {
        mvc.perform(post("/signup")
                        .param("email", "is-not-email")
                        .param("password","password")
                        .param("passwordConfirm","different")
                        .param("agreeTerms","false")
                        .param("phone","")
                        .param("name","")
                ).andExpect(status().isOk())
                .andExpect(view().name("signUpForm"))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "passwordConfirm"))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "agreeTerms"))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "phone"))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "name"))
                .andExpect(model().attributeHasFieldErrors("signUpForm", "email"));
    }

}