package dev.chan.orderpaymentservice.common;

import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;

public class CommandMother {

    /**
     * 기본 값을 가진 placeOrderCommand 생성
     *
     * @return 설정된 기본값을 가진 PlaceOrderCommand 를 리턴합니다.
     * <br/>기본값은 다음과 같습니다.
     * <br/>long memberId - 1L
     * <br/>long productId - 1L
     * <br/>int orderQuantity - 5
     */
    public static PlaceOrderCommand placeOrderCommand() {
        return new PlaceOrderCommand(1L, 1L, 5);
    }

    /**
     * 기본 값을 가진 placeOrderCommand 생성
     *
     * @return 설정된 기본값을 가진 PlaceOrderCommand 를 리턴합니다.
     * <br/>기본값은 다음과 같습니다.
     * <br/>long memberId - 1L
     * <br/>long productId - 1L
     * <br/>int orderQuantity - 5
     */
    public static PlaceOrderCommand withId(Long productId) {
        return new PlaceOrderCommand(1L, productId, 5);
    }

    /**
     * placeOrderCommand 생성 (orderQuantity)
     * 테스트시 orderQuantity 를 설정하여 테스트 하기위한 생성 편의 메서드입니다.
     *
     * @return 설정된 orderQuantity 를 받아 PlaceOrderCommand 를 생성해 리턴합니다.
     * <br/>기본값은 다음과 같습니다.
     * <br/>long memberId - 1L
     * <br/>long productId - 1L
     * <br/>int orderQuantity - {설정값}
     */
    public static PlaceOrderCommand withOrderQuantity(int orderQuantity) {
        return new PlaceOrderCommand(1L, 1L, orderQuantity);
    }
}
