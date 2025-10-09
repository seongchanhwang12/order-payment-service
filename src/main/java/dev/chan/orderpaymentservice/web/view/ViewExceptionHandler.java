package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.domain.member.DuplicateMemberException;
import dev.chan.orderpaymentservice.web.dto.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class ViewExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ModelAndView handle(DuplicateMemberException e) {
        SignUpForm form = new SignUpForm();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(form, "signUpForm");

        // TODO 두번째 파라미터는 메시지 서비스 구현시 조회된 메시지로 대체합니다.
        bindingResult.reject(e.getErrorCode().messageKey(), e.getErrorCode().messageKey());
        ModelAndView mv = new ModelAndView("signUpForm");
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + "signUpForm", bindingResult);
        mv.addObject("signUpForm", form);
        return mv;

    }

}
