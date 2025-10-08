package dev.chan.orderpaymentservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceOrderRequest {

    // TODO! 테스트를 위해 임시적으로 MemberId를 요청 데이터에 담아 받습니다. 추후 JWT 활용하도록 변경 예정입니다.
    @NotBlank
    private Long memberId;

    @NotBlank
    private Long productId;

    @NotBlank
    private int orderQuantity;

}
