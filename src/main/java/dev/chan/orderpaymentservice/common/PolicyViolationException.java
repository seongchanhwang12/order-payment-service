package dev.chan.orderpaymentservice.common;

import lombok.Getter;

/**
 * 도메인 정책 위반 예외
 * 도메인 예외 추가시 반드시 이 클래스를 상속해주세요.
 */
@Getter
public class PolicyViolationException extends DomainException {

    /**
     * 도메인 정책 위반시 발생하는 최상위 예외
     *
     * @param errorCode - 업무 공통 예외코드 추상화 인터페이스
     * @param message - 예외 발생시 디버깅을 위한 시스템 메시지
     */
    public PolicyViolationException(ErrorCode errorCode, String message) {
        super(errorCode,message);
    }
}
