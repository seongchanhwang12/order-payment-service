package dev.chan.orderpaymentservice.web.api;

import dev.chan.orderpaymentservice.application.member.GetMemberUseCase;
import dev.chan.orderpaymentservice.application.member.dto.MemberDetail;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.web.dto.LoginSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberApiControllerTest {

    @InjectMocks
    MemberApiController sut;

    @Mock
    GetMemberUseCase getMemberUseCase;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        mvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new ApiExceptionHandler())
                .setValidator(bean)
                .build();

    }

    @Test
    void GET_회원조회요청시_세션있으면_200응답및_회원정보를_반환한다() throws Exception {
        //given
        long memberId = 1L;
        String email = "email";
        String name = "name";
        MemberDetail memberDetail = new MemberDetail(email, name, "01011112222");
        doReturn(memberDetail).when(getMemberUseCase).handle(memberId);

        //when & then
        mvc.perform(get("/api/v1/members/me")
                    .sessionAttr("LOGIN_MEMBER", LoginSession.of(memberId,email,name))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.name").value(name));

        verify(getMemberUseCase, times(1)).handle(memberId);
    }

    @Test
    void GET_회원조회시_세션없으면_401응답및_에러정보를_반환한다() throws Exception {
        //given
        //when && then
        mvc.perform(get("/api/v1/members/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.status").value(MemberError.UNAUTHORIZED.status().value()))
                .andExpect(jsonPath("$.error.code").value(MemberError.UNAUTHORIZED.code()));

        then(getMemberUseCase).shouldHaveNoInteractions();
    }


}